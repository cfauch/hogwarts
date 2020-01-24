module com.code.fauch.hogwarts.core {
    requires transitive java.desktop;
    exports com.code.fauch.hogwarts.core.spi;
    exports com.code.fauch.hogwarts.core.model;
    exports com.code.fauch.hogwarts.core.model.clock;
    uses com.code.fauch.hogwarts.core.spi.IContentTypeProvider;
    uses com.code.fauch.hogwarts.core.spi.ITypeProvider;
    provides com.code.fauch.hogwarts.core.spi.ITypeProvider with com.code.fauch.hogwarts.core.model.StdType;
}
