package com.goldssss.sequence.core.cache;

public class SequencePageTemplage {
    public static final String SEQUENCE_PAGE_CONTEXT= "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "<script src=\"../../static/js/webfont.js\"></script>\n" +
            "<script src=\"../../static/js/snap.svg-min.js\"></script>\n" +
            "<script src=\"../../static/js/underscore-min.js\"></script>\n" +
            "<script src=\"../../static/js/sequence-diagram-min.js\"></script>\n" +
            "<script src=\"../../static/js/jquery-1.4.1.min.js\"></script>"+
            "<title>${title}</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<textarea id=\"sequenceContext\" hidden=\"hidden\">\n" +
            "    ${sequenceContext}\n" +
            "</textarea>\n" +
            "<div id=\"diagram\">\n" +
            "</div>\n" +
            "<script>\n" +
            "    var diagram = Diagram.parse($(\"#sequenceContext\").val());\n" +
            "    diagram.drawSVG(\"diagram\", {theme: 'simple'});\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";
}
