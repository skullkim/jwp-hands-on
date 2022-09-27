package nextstep.study.di.stage4.annotations;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.reflections.Reflections;

public class ClassPathScanner {

    public static Set<Class<?>> getAllClassesInPackage(final String packageName) {
        final Reflections reflections = new Reflections(packageName);
        return Stream.of(
                        reflections.getTypesAnnotatedWith(Service.class),
                        reflections.getTypesAnnotatedWith(Repository.class)
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
