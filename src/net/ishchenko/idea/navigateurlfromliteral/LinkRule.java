package net.ishchenko.idea.navigateurlfromliteral;

public class LinkRule {
    private String startsWith;
    private String navigateTo;

    public LinkRule(String navigateTo, String startsWith) {
        this.navigateTo = navigateTo;
        this.startsWith = startsWith;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public String getNavigateTo() {
        return navigateTo;
    }

    public void setNavigateTo(String navigateTo) {
        this.navigateTo = navigateTo;
    }
}
