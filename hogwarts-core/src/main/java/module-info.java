module com.code.fauch.hogwarts.core {
    requires transitive java.desktop;
    exports com.code.fauch.hogwarts.core.spi;
    exports com.code.fauch.hogwarts.core.model;
    uses com.code.fauch.hogwarts.core.spi.IContentTypeProvider;
    uses com.code.fauch.hogwarts.core.spi.ITypeProvider;
}
