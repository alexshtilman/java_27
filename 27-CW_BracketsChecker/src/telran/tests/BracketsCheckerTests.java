package telran.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import telran.expressions.BracketsChecker;

import static telran.expressions.BracketsChecker.*;
class BracketsCheckerTests {
String [][]brackets = {{"{", "}"},{"<table>", "</table>"},{"[","]"},{"/*", "*/"}, {"<", ">"}};
BracketsChecker bracketsChecker = new BracketsChecker(brackets);
	@Test
	void testParenthesesTrue() {
		assertTrue(parenthesesCheck("abcc(hhhh)"));
		assertTrue(parenthesesCheck("(((())))"));
		assertTrue(parenthesesCheck("((c(ab(lm))))"));
	}
	@Test
	void testParenthesesFalse() {
		assertFalse(parenthesesCheck("abcc(hh(hh)"));
		assertFalse(parenthesesCheck("(((()))"));
		assertFalse(parenthesesCheck("((c(ab)(lm))))"));
		assertFalse(parenthesesCheck(")("));
	}
	@Test 
	void testBracketsTrue() {
		assertTrue(bracketsChecker.bracketsCheck("<table>[(hhhh]</table>"));
		assertTrue(bracketsChecker.bracketsCheck("<table>aaaaaa[(hhhh]</table>"));
		assertTrue(bracketsChecker.bracketsCheck("<table>[{hhhh}]</table>"));
		assertTrue(bracketsChecker.bracketsCheck("/*{uuuu}[{;;;;;}]*/"));
		assertTrue(bracketsChecker.bracketsCheck("<<table>[{hhhh}]</table>>"));
	}
	@Test 
	void testBracketsFalse() {
		assertFalse(bracketsChecker.bracketsCheck("<table>[{hhhh]</table>"));
		assertFalse(bracketsChecker.bracketsCheck("<table>[{hhhh)]</table>"));
		assertFalse(bracketsChecker.bracketsCheck("/*{uu*/uu}[{;;;;;}]*/"));
		assertFalse(bracketsChecker.bracketsCheck("<<table>>[{hhhh}]</table>"));
	}

}