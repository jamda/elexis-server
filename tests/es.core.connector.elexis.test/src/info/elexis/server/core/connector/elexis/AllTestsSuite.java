package info.elexis.server.core.connector.elexis;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ch.elexis.core.services.IConfigService;
import ch.elexis.core.services.IElexisEntityManager;
import ch.elexis.core.services.IModelService;
import ch.elexis.core.test.initializer.TestDatabaseInitializer;
import ch.elexis.core.utils.OsgiServiceUtil;
import info.elexis.server.core.connector.elexis.services.AllServiceTests;

@RunWith(Suite.class)
//@SuiteClasses({ AllServiceTests.class, AllBillingTests.class })
@SuiteClasses({ AllServiceTests.class })
public class AllTestsSuite {
	
	private static IModelService modelService = OsgiServiceUtil.getService(IModelService.class).get();
	private static IElexisEntityManager entityManager = OsgiServiceUtil.getService(IElexisEntityManager.class).get();
	private static IConfigService configService = OsgiServiceUtil.getService(IConfigService.class).get();
	
	private static TestDatabaseInitializer initializer = new TestDatabaseInitializer(modelService, entityManager);
	public static String RWA_ID;

	@BeforeClass
	public static void setupClass() throws IOException, SQLException {		
		initializer.initializeDb(configService);
//
//		AllTestsSuite.getInitializer().initializePatient();
//		AllTestsSuite.getInitializer().initializeLaborTarif2009Tables();
//		AllTestsSuite.getInitializer().initializeAgendaTable();
//		AllTestsSuite.getInitializer().initializeArzttarifePhysioLeistungTables();
//		AllTestsSuite.getInitializer().initializeTarmedTables();
//		AllTestsSuite.getInitializer().initializeLaborItemsOrdersResults();
//		AllTestsSuite.getInitializer().initializeReminders();
//		AllTestsSuite.getInitializer().initializeLeistungsblockTables();
//		AllTestsSuite.getInitializer().initializeLabResult();
//		AllTestsSuite.getInitializer().initializeBehandlung();

//		IStock rowaStock = new StockService.Builder("RWA", 0).build();
//		rowaStock.setDriverUuid(MockStockCommissioningSystemDriverFactory.uuid.toString());
//		rowaStock.setDriverConfig("10.10.20.30:6050;defaultOutputDestination=2");
//		StockService.save(rowaStock);
//		RWA_ID = rowaStock.getId();
	}

//	public static TestDatabaseInitializer getInitializer() {
//		return initializer;
//	}
}
