package nextstep.study.di.stage3.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 스프링의 BeanFactory, ApplicationContext에 해당되는 클래스
 */
class DIContainer {

    private final Set<Object> beans;

    public DIContainer(final Set<Class<?>> classes) {
        this.beans = classes.stream()
                .map(this::createInstance)
                .collect(Collectors.toSet());
        injectDependency();
    }

    private Object createInstance(final Class<?> clazz) {
        try {
            final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void injectDependency() {
        for (Object bean : beans) {
            try {
                findBeanAndInject(bean);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    private void findBeanAndInject(final Object bean) throws IllegalAccessException {
        Field[] fields = bean.getClass()
                .getDeclaredFields();
        for (Field field : fields) {
            final Class<?> type = field.getType();
            beans.stream()
                    .filter(b -> type.isAssignableFrom(b.getClass()))
                    .findFirst()
                    .ifPresent(value -> setField(field, bean, value));
        }
    }

    private void setField(final Field field, final Object target, final Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(final Class<T> aClass) {
        return (T) beans.stream()
                .filter(bean -> bean.getClass().getName().equals(aClass.getName()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
