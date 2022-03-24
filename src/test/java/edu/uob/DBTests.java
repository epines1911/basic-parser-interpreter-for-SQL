package edu.uob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

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
    server.handleCommand("Create DATABASE aa ;");
    server.handleCommand("uSE aa ;");
    assertTrue(server.handleCommand("uSE aa ;").startsWith("[OK]"));
    assertEquals(server.getDbCtrl().getCurrentDB().name, "aa");
  }
  @Test
  void testCreateDB() {
    server.handleCommand("Create DATABASE aa;");
    assertTrue(server.handleCommand("Create DatABASe aa;").startsWith("[OK]"));
    assertEquals(server.getDbCtrl().getDBByName("aa").name, "aa");
  }
  //todo 测两个括号中间没内容的情况。

  @Test
  void testIsFloatLiteral() {
    Token t = new Token("+145.768");
    assertTrue(t.isFloatLiberal());
    t = new Token("-42.29509");
    assertTrue(t.isFloatLiberal());
    t = new Token("000756.2958");
    assertTrue(t.isFloatLiberal());
    t = new Token("+137.2349eeafe");
    assertFalse(t.isFloatLiberal());
    t = new Token("-13af7.29ag5");
    assertFalse(t.isFloatLiberal());
    t = new Token("dsfja");
    assertFalse(t.isFloatLiberal());
  }

  @Test
  void testIsIntegerLiteral() {
    Token t = new Token("+145");
    assertTrue(t.isIntegerLiberal());
    t = new Token("-42");
    assertTrue(t.isIntegerLiberal());
    t = new Token("000756");
    assertTrue(t.isIntegerLiberal());
    t = new Token("+1379eeafe");
    assertFalse(t.isIntegerLiberal());
    t = new Token("-13af729ag5");
    assertFalse(t.isIntegerLiberal());
    t = new Token("dsfja");
    assertFalse(t.isIntegerLiberal());
  }

  //todo is string literal
  //todo test "VALUE(())"


}
