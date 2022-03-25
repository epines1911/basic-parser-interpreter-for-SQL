package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class DBTests {

  private DBServer server;

  // we make a new server for every @Test (i.e. this method runs before every @Test test case)
  @BeforeEach
  void setup(@TempDir File dbDir) {
    // Notice the @TempDir annotation, this instructs JUnit to create a new temp directory somewhere
    // and proceeds to *delete* that directory when the test finishes.
    // You can read the specifics of this at
    // https://junit.org/junit5/docs/5.4.2/api/org/junit/jupiter/api/io/TempDir.html

    // If you want to inspect the content of the directory during/after a test run for debugging,
    // simply replace `dbDir` here with your own File instance that points to somewhere you know.
    // IMPORTANT: If you do this, make sure you rerun the tests using `dbDir` again to make sure it
    // still works and keep it that way for the submission.

    server = new DBServer(dbDir);
  }

  // Here's a basic test for spawning a new server and sending an invalid command,
  // the spec dictates that the server respond with something that starts with `[ERROR]`
  @Test
  void testInvalidCommandIsAnError() {
    assertTrue(server.handleCommand("foo").startsWith("[ERROR]"));
  }

  // Add more unit tests or integration tests here.
  // Unit tests would test individual methods or classes whereas integration tests are geared
  // towards a specific usecase (i.e. creating a table and inserting rows and asserting whether the
  // rows are actually inserted)
  @Test
  void testUseCmd() {
    server.handleCommand("Create DATABASE aa;");
    server.handleCommand("uSE aa;");
    assertTrue(server.handleCommand("uSE aa;").startsWith("[OK]"));
    assertEquals(server.getDbCtrl().getCurrentDB().name, "aa");
  }
  @Test
  void testCreateDB() {
    server.handleCommand("Create DATABASE aa;");
    assertTrue(server.handleCommand("Create DatABASe aa;").startsWith("[OK]"));
    assertEquals(server.getDbCtrl().getDBByName("aa").name, "aa");
  }

  @ParameterizedTest
  @ValueSource(strings = {"+137.2349eeafe", "-13af7.29ag5", "dsfja"})
  void testIsFloatFalse(String value) {
    Token t = new Token(value);
    assertFalse(t.isFloatLiberal());
  }


  @ParameterizedTest
  @ValueSource(strings = {"+145.768", "-42.29509", "000756.2958"})
  void testIsFloatTrue(String value) {
    Token t = new Token(value);
    assertTrue(t.isFloatLiberal());
  }

  @Test
  void testIsIntegerLiteral() {
    Token t = new Token("+145");
    assertTrue(t.isIntegerLiberal());
    t = new Token("-42");
    assertTrue(t.isIntegerLiberal());
    t = new Token("00756980");
    assertTrue(t.isIntegerLiberal());
    t = new Token("+1379eeafe");
    assertFalse(t.isIntegerLiberal());
    t = new Token("-13af729ag5");
    assertFalse(t.isIntegerLiberal());
    t = new Token("ds1543fja123");
    assertFalse(t.isIntegerLiberal());
  }

  @ParameterizedTest
  @ValueSource(strings = {"'d*&#63('", "'0(09)'", "sd'kf'", "'sdj.'f", "sd'f"})
  void testIsStringLiteral1(String clip) {
    Token t = new Token(clip);
    assertTrue(t.isStringLiteral());
  }

//  @ParameterizedTest
//  @ValueSource(strings = {
//          "Create table people(aa,bb,adfas);",
//          "INSERT INTO people VALUES((),'d*&#63(','0(09)',TRUE,145.098,0970,',0')",
//          "select * from sldfj where name==0",
//          "select * from sldfj where (age<=167)and(gender!='male');"})
//  void testModifyCommand(String command) {
//    Tokenizer tokenizer = new Tokenizer(command);
//  }

  @Test
  void testMultipleCmds() {
    server.handleCommand("Create DATABASE aa;");
    server.handleCommand("uSE aa;");
    server.handleCommand("create table ea;");
    server.handleCommand("create table cada(name, age, school);");
    server.handleCommand("insert into cada values('ww', 18, 'abcd1');");
    server.handleCommand("insert into cada values('rr', 21, 'qwer2');");
    server.handleCommand("create table adfs;");
    server.handleCommand("drop table ea;");
    server.handleCommand("alter table cada drop school;");
    server.handleCommand("alter table cada add school2;");
    assertTrue(server.handleCommand("select * from cada;").startsWith("[OK]"));
    server.handleCommand("drop database aa;");
  }

  @Test
  void testJoinCommand() {
    server.handleCommand("CREATE DATABASE markbook;");
    server.handleCommand("USE markbook;");
    server.handleCommand("CREATE TABLE marks (name, mark, pass);");
    server.handleCommand("INSERT INTO marks VALUES ('Steve', 65, TRUE);");
    server.handleCommand("INSERT INTO marks VALUES ('Dave', 55, TRUE);");
    server.handleCommand("INSERT INTO marks VALUES ('Bob', 35, FALSE);");
    server.handleCommand("INSERT INTO marks VALUES ('Clive', 20, FALSE);");
    server.handleCommand("SELECT * FROM marks;");
    server.handleCommand("CREATE TABLE coursework (task, grade);");
    server.handleCommand("INSERT INTO coursework VALUES ('OXO', 3);");
    server.handleCommand("INSERT INTO coursework VALUES ('DB', 1);");
    server.handleCommand("INSERT INTO coursework VALUES ('OXO', 4);");
    server.handleCommand("INSERT INTO coursework VALUES ('STAG', 2);");
    server.handleCommand("SELECT * FROM coursework;");
    server.handleCommand("JOIN coursework AND marks ON grade AND id;");
  }

}
