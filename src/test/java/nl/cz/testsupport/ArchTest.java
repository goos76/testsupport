package nl.cz.testsupport;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("nl.cz.testsupport");

        noClasses()
            .that()
            .resideInAnyPackage("nl.cz.testsupport.service..")
            .or()
            .resideInAnyPackage("nl.cz.testsupport.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..nl.cz.testsupport.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
