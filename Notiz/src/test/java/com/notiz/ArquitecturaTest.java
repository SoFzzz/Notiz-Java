package com.notiz;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.notiz", importOptions = ImportOption.DoNotIncludeTests.class)
class ArquitecturaTest {

    @ArchTest
    static final ArchRule capas = layeredArchitecture().consideringOnlyDependenciesInLayers()
            .layer("Presentacion").definedBy("..presentacion..")
            .layer("Negocio").definedBy("..negocio..")
            .layer("Datos").definedBy("..datos..")
            .whereLayer("Presentacion").mayNotBeAccessedByAnyLayer()
            .whereLayer("Negocio").mayOnlyBeAccessedByLayers("Presentacion")
            .whereLayer("Datos").mayOnlyBeAccessedByLayers("Negocio");

    @ArchTest
    static final ArchRule auditoriaNoUsaDatosDeCalificaciones = noClasses().that().resideInAPackage("..auditoria..")
            .should().dependOnClassesThat().resideInAPackage("..calificaciones.datos..");

    @ArchTest
    static final ArchRule notificacionesNoUsaDatosDeCalificaciones = noClasses().that().resideInAPackage("..notificaciones..")
            .should().dependOnClassesThat().resideInAPackage("..calificaciones.datos..");

    @ArchTest
    static final ArchRule notificacionesNoDependeDeAuditoria = noClasses().that().resideInAPackage("..notificaciones..")
            .should().dependOnClassesThat().resideInAPackage("..auditoria..");

    @ArchTest
    static final ArchRule auditoriaNoDependeDeNotificaciones = noClasses().that().resideInAPackage("..auditoria..")
            .should().dependOnClassesThat().resideInAPackage("..notificaciones..");

    @ArchTest
    static final ArchRule datosNoDependeDeSpringWeb = noClasses().that().resideInAPackage("..datos..")
            .should().dependOnClassesThat().resideInAPackage("org.springframework.web..");
}
