package echo;

public class MathHandler extends RequestHandler {

    @Override
    protected String response(String msg) throws Exception {
        if (msg == null || msg.trim().isEmpty()) return "Invalid input";

        String[] tokens = msg.trim().split("\\s+");
        if (tokens.length < 2) return "Usage: operator num num ...";

        String op = tokens[0];
        double result;

        try {
            result = Double.parseDouble(tokens[1]);
            for (int i = 2; i < tokens.length; i++) {
                double num = Double.parseDouble(tokens[i]);
                switch (op) {
                    case "add": result += num; break;
                    case "sub": result -= num; break;
                    case "mul": result *= num; break;
                    case "div": result /= num; break;
                    default: return "Unknown operator: " + op;
                }
            }
        } catch (NumberFormatException e) {
            return "Invalid number format";
        }

        return String.valueOf(result);
    }
}

