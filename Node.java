
//計算木を生成コード

import java.math.BigDecimal;
import java.math.MathContext;

abstract class Node {
	abstract public void setOp(char c);

	abstract public void addLeft(Node n);

	abstract public void addRight(Node n);

	abstract public double calculate();

	abstract public int Icalculate();

	abstract public String displayValue();
}

class Num extends Node {
	public Num(double n) {
		value = n;
	}

	private double value;

	@Override
	public void setOp(char c) {
	}

	@Override
	public void addLeft(Node n) {
	}

	@Override
	public void addRight(Node n) {
	}

	@Override
	public double calculate() {
		return value;
	}

	@Override
	public int Icalculate() {
		return (int) value;
	}

	@Override
	public String displayValue() {
		if ((value - (int) value) == 0) {
			return String.format("" + (int) value);
		} else {
			return String.format("" + value);
		}
	}
}

class Op extends Node {
	public static Node connectToLeft(Node n) {
		final Op result = new Op();
		result.left = n;
		return result;
	}

	public Op() {
	}

	private char op;
	private Node left;
	private Node right;

	@Override
	public void setOp(char c) {
		op = c;
	}

	@Override
	public void addLeft(Node n) {
		left = n;
	}

	@Override
	public void addRight(Node n) {
		right = n;
	}

	@Override
	public double calculate() {
		if (left != null && right != null) {
			double leftOp = left.calculate();
			double rightOp = right.calculate();
			BigDecimal leftOp01 = BigDecimal.valueOf(leftOp);
			BigDecimal rightOp01 = BigDecimal.valueOf(rightOp);

			BigDecimal aResult = leftOp01.add(rightOp01, MathContext.DECIMAL64);
			BigDecimal sResult = leftOp01.subtract(rightOp01, MathContext.DECIMAL64);
			BigDecimal mResult = leftOp01.multiply(rightOp01, MathContext.DECIMAL64);
			BigDecimal dResult = leftOp01.divide(rightOp01, MathContext.DECIMAL64);
			BigDecimal rResult = leftOp01.remainder(rightOp01, MathContext.DECIMAL64);

			if (op == '+')
				return aResult.doubleValue();
			else if (op == '-')
				return sResult.doubleValue();
			else if (op == '*')
				return mResult.doubleValue();
			else if (op == '/')
				return dResult.doubleValue();
			else if (op == '%')
				return rResult.doubleValue();
			else
				return 0.0;
		} else {
			return 0.0;
		}
	}

	@Override
	public int Icalculate() {
		if (left != null && right != null) {
			int leftOp = (int) left.calculate();
			int rightOp = (int) right.calculate();

			if (op == '+')
				return leftOp + rightOp;
			else if (op == '-')
				return leftOp - rightOp;
			else if (op == '*')
				return leftOp * rightOp;
			else if (op == '/')
				return leftOp / rightOp;
			else if (op == '%')
				return leftOp % rightOp;	
			else
				return 0;
		} else {
			return 0;
		}
	}

	@Override
	public String displayValue() {
		if (left != null && right != null) {
			String leftStr = left.displayValue();
			String rightStr = right.displayValue();
			String str = "(" + leftStr + String.valueOf(op) + rightStr + ")";
			return str;
		} else {
			return String.valueOf(op);
		}
	}
}
