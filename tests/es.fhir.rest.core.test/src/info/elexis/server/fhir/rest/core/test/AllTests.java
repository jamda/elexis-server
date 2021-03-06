package info.elexis.server.fhir.rest.core.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import ch.elexis.core.findings.IFinding;
import ch.elexis.core.findings.IFindingsService;
import info.elexis.server.core.connector.elexis.jpa.test.TestDatabaseInitializer;
import info.elexis.server.fhir.rest.core.resources.AllergyIntoleranceTest;
import info.elexis.server.fhir.rest.core.resources.AppointmentTest;
import info.elexis.server.fhir.rest.core.resources.CORSTest;
import info.elexis.server.fhir.rest.core.resources.ClaimTest;
import info.elexis.server.fhir.rest.core.resources.CodesySystemTest;
import info.elexis.server.fhir.rest.core.resources.ConditionTest;
import info.elexis.server.fhir.rest.core.resources.CoverageTest;
import info.elexis.server.fhir.rest.core.resources.EncounterTest;
import info.elexis.server.fhir.rest.core.resources.FamilyMemberHistoryTest;
import info.elexis.server.fhir.rest.core.resources.MedicationRequestTest;
import info.elexis.server.fhir.rest.core.resources.ObservationTest;
import info.elexis.server.fhir.rest.core.resources.OrganizationTest;
import info.elexis.server.fhir.rest.core.resources.PatientTest;
import info.elexis.server.fhir.rest.core.resources.PractitionerRoleTest;
import info.elexis.server.fhir.rest.core.resources.ProcedureRequestTest;
import info.elexis.server.fhir.rest.core.resources.ScheduleTest;

@RunWith(Suite.class)
@SuiteClasses({ CORSTest.class, MedicationRequestTest.class, PatientTest.class, OrganizationTest.class, CoverageTest.class,
		PractitionerRoleTest.class, EncounterTest.class, ConditionTest.class, CodesySystemTest.class,
		ProcedureRequestTest.class, ClaimTest.class, ObservationTest.class, FamilyMemberHistoryTest.class,
		AllergyIntoleranceTest.class, AppointmentTest.class, ScheduleTest.class })
public class AllTests {

	private static IFindingsService iFindingsService;
	
	public static final String GENERIC_CLIENT_URL = "http://localhost:8380/fhir";

	private static TestDatabaseInitializer testDatabaseInitializer = new TestDatabaseInitializer();

	public static Date getDate(LocalDateTime localDateTime) {
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}

	public static LocalDateTime getLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static boolean isCodeInConcept(String system, String code, CodeableConcept concept) {
		List<Coding> list = concept.getCoding();
		if (list != null && !list.isEmpty()) {
			for (Coding coding : list) {
				if (coding.getSystem().equals(system) && coding.getCode().equals(code)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isCodeInConcepts(String system, String code, List<CodeableConcept> concepts) {
		if (concepts != null && !concepts.isEmpty()) {
			for (CodeableConcept concept : concepts) {
				if (isCodeInConcept(system, code, concept)) {
					return true;
				}
			}
		}
		return false;
	}

	public static IFindingsService getFindingsService() {
		if (iFindingsService != null) {
			return iFindingsService;
		}
		BundleContext bundleContext = FrameworkUtil.getBundle(AllTests.class).getBundleContext();
		ServiceReference<IFindingsService> serviceReference = bundleContext.getServiceReference(IFindingsService.class);
		if (serviceReference != null) {
			iFindingsService = bundleContext.getService(serviceReference);
		}
		return iFindingsService;
	}

	public static void deleteAllFindings() {
		IFindingsService iFindingsService = getFindingsService();
		if (iFindingsService != null) {
			for (IFinding iFinding : iFindingsService.getPatientsFindings(testDatabaseInitializer.getPatient().getId(),
					IFinding.class)) {
				iFindingsService.deleteFinding(iFinding);
			}
		}
	}

	public static TestDatabaseInitializer getTestDatabaseInitializer() {
		return testDatabaseInitializer;
	}
}
