package eu.kielczewski.akanke.web.service.viewhelper;

class ExcerptExtractor {

    private static final String EXCERPT_END_TAG = "<!--more-->";

    public String extract(String source, String excerptEndTagReplacement) {
        int i = source.indexOf(EXCERPT_END_TAG);
        if (i == -1) {
            return source;
        }
        return source
                .substring(0, i + EXCERPT_END_TAG.length())
                .replace(EXCERPT_END_TAG, excerptEndTagReplacement);
    }

}
