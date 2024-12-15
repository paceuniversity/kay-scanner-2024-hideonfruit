public class Token {

    // Token type: Identifier, Keyword, Literal, Separator, Operator, or Other
    private String type;
    private String value;

    /**
     * Set the value of a Token.
     * @param value Token value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the value of a Token.
     * @return Token value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the type of a Token.
     * @param type Token type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the type of a Token.
     * @return Token type
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Value: " + this.getValue() + " Type: " + this.getType();
    }
}
