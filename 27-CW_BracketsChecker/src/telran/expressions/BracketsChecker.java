package telran.expressions;

import java.util.*;

public class BracketsChecker {
	HashMap<String, String> closeOpenBrackets = new HashMap<>();
	HashSet<String> openBrackets = new HashSet<>();
	TreeSet<String> allBrackets = new TreeSet<>();

	public BracketsChecker(String[][] bracketsData) {
		for (String[] brackets : bracketsData) {
			closeOpenBrackets.put(brackets[1], brackets[0]);
			openBrackets.add(brackets[0]);
			allBrackets.add(brackets[0]);
			allBrackets.add(brackets[1]);
		}
	}

	public static boolean parenthesesCheck(String expression) {

		int count = 0;
		for (char sym : expression.toCharArray()) {
			if (sym == '(') {
				count++;
			} else if (sym == ')') {
				count--;
				if (count < 0) {
					return false;
				}
			}
		}
		return count == 0;
	}

	public boolean bracketsCheck(String expression) {
		String[] brackets = getBrackets(expression);

		return check(brackets);
	}

	private boolean check(String[] brackets) {
		Stack<String> stackBrackets = new Stack<>();
		for (String bracket : brackets) {
			if (openBrackets.contains(bracket)) {
				stackBrackets.push(bracket);
			} else {
				if (stackBrackets.isEmpty()) {
					return false;
				}
				if (closeOpenBrackets.get(bracket).equals(stackBrackets.peek())) {
					stackBrackets.pop();
				} else {
					return false;
				}
			}
		}
		return stackBrackets.isEmpty();
	}

	/**
	 * Extracts all 'brackets' from specified 'expression' text. In case if two ore
	 * more 'brackets' are matched in the same position, the longest one is taken.
	 */
	private String[] getBrackets(String expression) {
		List<String> foundBrackets = new ArrayList<>();
		for (int position = 0; position < expression.length();) {
			String bracket = matchLongestBracket(expression, position);
			if (bracket != null) {
				foundBrackets.add(bracket);
				position += bracket.length();
				continue;
			}
			position++;
		}
		return foundBrackets.toArray(new String[foundBrackets.size()]);
	}

	/**
	 * Matches the longest 'bracket' starting from the specified position in
	 * 'expression' text.
	 */
	private String matchLongestBracket(String expression, int position) {
		SortedSet<String> bracketsSubset = allBrackets;
		String lastFoundBracket = null;
		for (int prefixLen = 1; prefixLen <= expression.length() - position; prefixLen++) {
			String prefix = expression.substring(position, position + prefixLen);
			bracketsSubset = findWithPrefix(bracketsSubset, prefix);
			if (bracketsSubset.isEmpty()) {
				break; // no brackets with such prefix, stop to search from current position
			}
			if (bracketsSubset.first().equals(prefix)) {
				lastFoundBracket = prefix;
			}
			// brackets starting with prefix exist, continue search with longer prefix
		}
		return lastFoundBracket;
	}

	/**
	 * Finds in 'src' set the sorted subset of words with specified prefix
	 */
	private static SortedSet<String> findWithPrefix(SortedSet<String> src, String prefix) {
		StringBuilder upperBound = new StringBuilder(prefix);
		upperBound.setCharAt(upperBound.length() - 1, (char) (upperBound.charAt(upperBound.length() - 1) + 1));
		return src.subSet(prefix, upperBound.toString());
	}
}