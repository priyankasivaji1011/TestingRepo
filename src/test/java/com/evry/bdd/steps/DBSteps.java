package com.evry.bdd.steps;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.Status;
import com.evry.bdd.utils.DBConnectionUtil;
import com.evry.bdd.utils.ExtentTestManager;
import com.evry.bdd.utils.LoggerHelper;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DBSteps {

	private static int insertedId;
	private static final Logger log = LoggerHelper.getLogger(DBSteps.class);
	@Given("I insert a new person record")
	public void insertRecord() throws SQLException {
		insertedId = (int) (Math.random() * 100000);
		String insertQuery = "INSERT INTO TestPerson (ID, FirstName, LastName, Age) VALUES (?, ?, ?, ?)";

		try {
			DBConnectionUtil.executeUpdate(insertQuery, insertedId, "John", "Doe", 30);
			log.info("Inserted new record with ID: " + insertedId);
			System.out.println("Inserted new record with ID: " + insertedId);
			ExtentTestManager.getTest().log(Status.PASS, "Inserted new record with ID: " + insertedId);
		} catch (SQLException e) {
			ExtentTestManager.getTest().log(Status.FAIL, "Insert failed: " + e.getMessage());
			throw e;
		}
	}

	@When("I update the person record")
	public void updateRecord() throws SQLException {
		String sql = "UPDATE TestPerson SET FirstName = ? WHERE ID = ?";

		try {
			DBConnectionUtil.executeUpdate(sql, "Anu", insertedId);
			log.info("Updated record with ID " + insertedId);
			System.out.println("Updated record with ID " + insertedId);
			ExtentTestManager.getTest().log(Status.PASS, "Updated record ID: " + insertedId + " with name 'Anu'");
		} catch (SQLException e) {
			ExtentTestManager.getTest().log(Status.FAIL, "Update failed for ID: " + insertedId + " → " + e.getMessage());
			throw e;
		}
	}

	@Then("I verify the updated person record")
	public void verifyPerson() throws SQLException {
		String sql = "SELECT FirstName FROM TestPerson WHERE ID = ?";
		try {
			ResultSet rs = DBConnectionUtil.executeQuery(sql, insertedId);
			if (rs.next()) {
				String firstName = rs.getString("FirstName");
				if ("Anu".equals(firstName)) {
					log.info("Verified updated name: " + firstName);
					System.out.println("Verified updated name: " + firstName);
					ExtentTestManager.getTest().log(Status.PASS, "Verified updated record: ID " + insertedId + ", FirstName = " + firstName);
				} else {
					String msg = "Name mismatch. Found: " + firstName;
					ExtentTestManager.getTest().log(Status.FAIL, msg);
					throw new AssertionError(msg);
				}
			} else {
				String msg = "Record not found!";
				ExtentTestManager.getTest().log(Status.FAIL, msg);
				throw new AssertionError(msg);
			}
		} catch (SQLException e) {
			ExtentTestManager.getTest().log(Status.FAIL, "Verification query failed: " + e.getMessage());
			throw e;
		}
	}

	@And("I delete the person record")
	public void deleteRecord() throws SQLException {
		String sql = "DELETE FROM TestPerson WHERE ID = ?";

		try {
			DBConnectionUtil.executeUpdate(sql, insertedId);
			//log.info("Deleted record with ID " + insertedId);
			System.out.println("Deleted record with ID " + insertedId);
			ExtentTestManager.getTest().log(Status.PASS, "Deleted record with ID " + insertedId);
		} catch (SQLException e) {
			ExtentTestManager.getTest().log(Status.FAIL, "Delete failed for ID " + insertedId + " → " + e.getMessage());
			throw e;
		}
	}

	@Then("the person record should not exist")
	public void verifyDelete() throws SQLException {
		String sql = "SELECT COUNT(*) AS cnt FROM TestPerson WHERE ID = ?";

		try {
			ResultSet rs = DBConnectionUtil.executeQuery(sql, insertedId);
			if (rs.next() && rs.getInt("cnt") == 0) {
				//System.out.println("Record deleted successfully (ID " + insertedId + ").");
				log.info("Record deleted successfully (ID " + insertedId + ").");
				ExtentTestManager.getTest().log(Status.PASS, "Record deleted successfully (ID " + insertedId + ")");
			} else {
				String msg = "❌ Record still exists after delete!";
				ExtentTestManager.getTest().log(Status.FAIL, msg);
				throw new AssertionError(msg);
			}
		} catch (SQLException e) {
			ExtentTestManager.getTest().log(Status.FAIL, "Delete verification failed: " + e.getMessage());
			throw e;
		}
	}


	@Given("I select a record from table")
	public void selectAllRecords() throws SQLException {
		String sql = "select * from HumanResources.Department";

		try {
			ResultSet rs = DBConnectionUtil.executeQuery(sql);
			System.out.println("All Records in TestPerson:");
			while (rs.next()) {
				int id = rs.getInt("DepartmentId");
				String firstName = rs.getString("Name");
				String lastName = rs.getString("GroupName");
				java.sql.Timestamp modifiedDate = rs.getTimestamp("ModifiedDate");
				//System.out.println("ID: " + id + ", FirstName: " + firstName + ", LastName: " + lastName + ", Date: " +modifiedDate);
				log.info("ID: " + id + ", FirstName: " + firstName + ", LastName: " + lastName + ", Date: " +modifiedDate);
				ExtentTestManager.getTest().log(Status.PASS,"ID: " + id + ", FirstName: " + firstName + ", LastName: " + lastName + ", Date: " +modifiedDate);
			}
			//ExtentTestManager.getTest().log(Status.PASS, "Selected all records from Department table.");
		} catch (SQLException e) {
			ExtentTestManager.getTest().log(Status.FAIL, "Select all records failed: " + e.getMessage());
			throw e;
		}
	}


	@When("Update specific ids data from table")
	public void updateSpecificRecord() throws SQLException {
		String sql = "UPDATE HumanResources.Department SET Name = 'TestingSQL' WHERE DepartmentID = 1";

		try {
			int rowsUpdated = DBConnectionUtil.executeUpdate(sql);
			//System.out.println("Rows updated: " + rowsUpdated);
			log.info("Updated DepartmentID 1 with Name='TestingSQL'");
			ExtentTestManager.getTest().log(Status.PASS, "Updated DepartmentID 1 with Name='TestingSQL'");
		} catch (SQLException e) {
			ExtentTestManager.getTest().log(Status.FAIL, "Update failed: " + e.getMessage());
			throw e;
		}
	}


	@Then("select employee who is single")
	public void selectSingleEmployees() throws SQLException {
		String sql = "SELECT NationalIDNumber, JobTitle FROM HumanResources.Employee WHERE MaritalStatus = 'S'";

		try {
			ResultSet rs = DBConnectionUtil.executeQuery(sql);
			System.out.println("Details:");
			log.info("Selecting employees with MaritalStatus='S'");
			while (rs.next()) {
				int NationalIDNumber = rs.getInt("NationalIDNumber");	
				String jobTitile	 = rs.getString("JobTitle");
				System.out.println("NationalIDNumber: " + NationalIDNumber + ", jobTitile: " + jobTitile);
				//log.info("NationalIDNumber: " + NationalIDNumber + ", jobTitile: " + jobTitile);
				ExtentTestManager.getTest().log(Status.PASS, "NationalIDNumber: " + NationalIDNumber + ", jobTitile: " + jobTitile);
			}
			ExtentTestManager.getTest().log(Status.PASS,"Data retrieved successfully");
		} catch (SQLException e) {
			ExtentTestManager.getTest().log(Status.FAIL, "Select single employees failed: " + e.getMessage());
			throw e;
		}
	}

}

