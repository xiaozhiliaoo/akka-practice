package org.lili.akka.learningakka.ch5;

import java.util.List;

public class TestHelper {
    public static String file = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<!-- saved from url=(0031)http://www.lipsum.com/feed/html -->\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">\n" +
            "<title>Lorem Ipsum - All the facts - Lipsum generator</title>\n" +
            "<meta name=\"keywords\" content=\"Lorem Ipsum, Lipsum, Lorem, Ipsum, Text, Generate, Generator, Facts, Information, What, Why, Where, Dummy Text, Typesetting, Printing, de Finibus, Bonorum et Malorum, de Finibus Bonorum et Malorum, Extremes of Good and Evil, Cicero, Latin, Garbled, Scrambled, Lorem ipsum dolor sit amet, dolor, sit amet, consectetur, adipiscing, elit, sed, eiusmod, tempor, incididunt\">\n" +
            "\n" +
            "<meta http-equiv=\"content-language\" content=\"en\">\n" +
            "<link rel=\"shortcut icon\" href=\"http://www.lipsum.com/favicon.ico\">\n" +
            "<style type=\"text/css\">\n" +
            "\n" +
            "#lipsum {font-size:11px;text-align:justify}\n" +
            "#generated {font-size:11px;font-weight:bold;text-align:left}\n" +
            "#lipsumta {font-size:11px;text-align:justify;font-family:Arial,Helvetica,sans;width:700px;height:300px;padding:3px 0px 3px 5px;border:1px solid #666;margin:0px 0px 5px 0px}\n" +
            "\n" +
            "a:link,a:visited,a:active {color:#000000}\n" +
            "a:hover {color:#ff0000}\n" +
            "\n" +
            "body {background:url(/images/body.gif);font-family:Arial,Helvetica,sans}\n" +
            "body,td,input,form,div {margin:0;font-size:11px;font-family:Arial,Helvetica,sans}\n" +
            "#Outer {text-align:center}\n" +
            "#Inner710 {width:710px;margin:0px auto;padding-bottom:10px}\n" +
            "#Inner734 {width:734px;margin:0px auto;padding-bottom:10px}\n" +
            "#BannerO {margin:0px auto;width:734px}\n" +
            "#BannerI {text-align:center;margin-top:15px}\n" +
            "#Languages {margin:20px 0px;clear:both}\n" +
            "#Languages a {padding:1px 6px 1px 22px;font-size:11px;line-height:20px}\n" +
            "#Languages a.zz, #Languages a.zz:hover {text-decoration:none;color:#000000}\n" +
            "#Languages a.xx {padding:0px}\n" +
            "#Languages img {vertical-align:middle;margin-right:4px}\n" +
            "a.hy{background:url(/images/300811.png) no-repeat 0px -400px}\n" +
            "a.sq{background:url(/images/300811.png) no-repeat 0px -760px}\n" +
            "a.ar{background:url(/images/300811.png) no-repeat 0px -40px}\n" +
            "a.bg{background:url(/images/300811.png) no-repeat 0px -80px}\n" +
            "a.ca{background:url(/images/300811.png) no-repeat 0px -120px}\n" +
            "a.hr{background:url(/images/300811.png) no-repeat 0px -360px}\n" +
            "a.cs{background:url(/images/300811.png) no-repeat 0px -140px}\n" +
            "a.da{background:url(/images/300811.png) no-repeat 0px -160px}\n" +
            "a.nl{background:url(/images/300811.png) no-repeat 0px -560px}\n" +
            "a.en{background:url(/images/300811.png) no-repeat 0px -220px}\n" +
            "a.et{background:url(/images/300811.png) no-repeat 0px -260px}\n" +
            "a.fi{background:url(/images/300811.png) no-repeat 0px -300px}\n" +
            "a.fr{background:url(/images/300811.png) no-repeat 0px -320px}\n" +
            "a.ka{background:url(/images/300811.png) no-repeat 0px -460px}\n" +
            "a.de{background:url(/images/300811.png) no-repeat 0px -180px}\n" +
            "a.el{background:url(/images/300811.png) no-repeat 0px -200px}\n" +
            "a.he{background:url(/images/300811.png) no-repeat 0px -340px}\n" +
            "a.hu{background:url(/images/300811.png) no-repeat 0px -380px}\n" +
            "a.id{background:url(/images/300811.png) no-repeat 0px -420px}\n" +
            "a.it{background:url(/images/300811.png) no-repeat 0px -440px}\n" +
            "a.lv{background:url(/images/300811.png) no-repeat 0px -500px}\n" +
            "a.lt{background:url(/images/300811.png) no-repeat 0px -480px}\n" +
            "a.mk{background:url(/images/300811.png) no-repeat 0px -520px}\n" +
            "a.ms{background:url(/images/300811.png) no-repeat 0px -540px}\n" +
            "a.no{background:url(/images/300811.png) no-repeat 0px -580px}\n" +
            "a.pl{background:url(/images/300811.png) no-repeat 0px -600px}\n" +
            "a.pt{background:url(/images/300811.png) no-repeat 0px -620px}\n" +
            "a.ro{background:url(/images/300811.png) no-repeat 0px -640px}\n" +
            "a.ru{background:url(/images/300811.png) no-repeat 0px -660px}\n" +
            "a.sr{background:url(/images/300811.png) no-repeat 0px -780px}\n" +
            "a.sk{background:url(/images/300811.png) no-repeat 0px -700px}\n" +
            "a.sl{background:url(/images/300811.png) no-repeat 0px -720px}\n" +
            "a.es{background:url(/images/300811.png) no-repeat 0px -240px}\n" +
            "a.sv{background:url(/images/300811.png) no-repeat 0px -800px}\n" +
            "a.th{background:url(/images/300811.png) no-repeat 0px -840px}\n" +
            "a.tr{background:url(/images/300811.png) no-repeat 0px -860px}\n" +
            "a.uk{background:url(/images/300811.png) no-repeat 0px -880px}\n" +
            "a.vi{background:url(/images/300811.png) no-repeat 0px -900px}\n" +
            "\n" +
            "#Packages a {margin:0px 5px}\n" +
            "#Footer a {margin:10px;color:#808080}\n" +
            ".lc {clear:left;float:left;width:348px}\n" +
            ".rc {clear:right;float:right;width:348px}\n" +
            ".lc div {float:left;text-align:left}\n" +
            ".rc div {float:left;text-align:left}\n" +
            "\n" +
            "p {text-align:justify;font-size:11px;line-height:14px;margin:0px 0px 14px 0px;padding:0}\n" +
            "\n" +
            "h1,h2,h3,h4,h5 {margin:0;padding:0;font-weight:normal}\n" +
            "h1 {background:url(/images/lorem.gif) no-repeat center top;height:60px}\n" +
            "h1 span {display:none}\n" +
            "h2 {font-size:12px;height:26px;text-align:left}\n" +
            "h2 span {display:none}\n" +
            "h2.what {background:url(/images/en/heading.gif) no-repeat 0px 0px}\n" +
            "h2.where {background:url(/images/en/heading.gif) no-repeat 0px -26px}\n" +
            "h2.why {background:url(/images/en/heading.gif) no-repeat 0px -52px}\n" +
            "h2.getsome {background:url(/images/en/heading.gif) no-repeat 0px -78px}\n" +
            "h3 {margin-bottom:14px;font-size:11px;font-weight:bold;text-align:left}\n" +
            "h4 {margin-top:14px;font-size:13px;text-align:center;font-style:italic}\n" +
            "h5 {margin-bottom:14px;font-size:11px;text-align:center}\n" +
            "\n" +
            ".box {clear:both;margin-top:6px;padding-top:6px;border-top:1px solid #666666}\n" +
            ".banners {clear:both;margin-top:14px;padding-top:14px;border-top:1px solid #d0d0d0}\n" +
            ".banners img {margin:2px}\n" +
            "\n" +
            "a.lnk:link,a.lnk:visited,a.lnk:active,a.lnk:hover {font-weight:bold;color:#ff0000}\n" +
            "\n" +
            "input#amount {width:40px;text-align:right}\n" +
            "input#generate {width:160px;text-align:center;margin-top:15px}\n" +
            "\n" +
            "div.start {width:30px;margin-top:4px;margin-bottom:12px;text-align:center}\n" +
            "\n" +
            "#HdrLeft {float:left;text-align:left;width:160px}\n" +
            "#HdrRight {float:right;text-align:right;width:160px;line-height:14px}\n" +
            "\n" +
            "#feedtable td {text-align:left;vertical-align:middle}\n" +
            "#typetable td {text-align:left;vertical-align:middle;height:20px}\n" +
            "\n" +
            "#Partner {text-align:left;margin:0;padding:0}\n" +
            "#Partner h1 {background:none;font-size:20px;height:20px;margin:0 0 10px 0;padding:0;font-weight:bold}\n" +
            "#Partner h2 {background:none;font-size:14px;height:14px;margin:0 0 10px 0;padding:0;font-weight:bold}\n" +
            "\n" +
            ".ltr {direction:ltr;unicode-bidi:embed}\n" +
            "\n" +
            "</style><style type=\"text/css\">* {}</style>\n" +
            "<!--[if IE 6]><style type=\"text/css\">#Languages a {line-height:16px;height:15px;margin-top:4px}</style><![endif]-->\n" +
            "\n" +
            "<style type=\"text/css\"></style></head>\n" +
            "<body>\n" +
            "\n" +
            "<div id=\"Outer\">\n" +
            "<div id=\"BannerO\"><div id=\"BannerI\"><script type=\"text/javascript\"><!--\n" +
            "    kmn_placement = '152788abd83d0b3c0dbb2cce9edbc7ad';\n" +
            "//--></script>\n" +
            "<script type=\"text/javascript\" src=\"http://cdn.komoona.com/scripts/kmn_sa.js\"></script></div></div>\n" +
            "<div id=\"Inner710\">\n" +
            "\n" +
            "<div id=\"Languages\"><a class=\"hy\" href=\"http://hy.lipsum.com/\">&#1344;&#1377;&#1397;&#1381;&#1408;&#1381;&#1398;</a> <a class=\"sq\" href=\"http://sq.lipsum.com/\">Shqip</a> <span class=\"ltr\" dir=\"ltr\"><a class=\"xx\" href=\"http://ar.lipsum.com/\"><img src=\"./lorem_files/ar.gif\" width=\"18\" height=\"12\" border=\"0\" alt=\"&#8235;&#1575;&#1604;&#1593;&#1585;&#1576;&#1610;&#1577;\"></a><a class=\"xx\" href=\"http://ar.lipsum.com/\">&#8235;&#1575;&#1604;&#1593;&#1585;&#1576;&#1610;&#1577;</a></span>&nbsp;&nbsp; <a class=\"bg\" href=\"http://bg.lipsum.com/\">&#1041;&#1098;&#1083;&#1075;&#1072;&#1088;&#1089;&#1082;&#1080;</a> <a class=\"ca\" href=\"http://ca.lipsum.com/\">Catal�</a> <a class=\"hr\" href=\"http://hr.lipsum.com/\">Hrvatski</a> <a class=\"cs\" href=\"http://cs.lipsum.com/\">&#268;esky</a> <a class=\"da\" href=\"http://da.lipsum.com/\">Dansk</a> <a class=\"nl\" href=\"http://nl.lipsum.com/\">Nederlands</a> <a class=\"en zz\" href=\"http://www.lipsum.com/\">English</a> <br><a class=\"et\" href=\"http://et.lipsum.com/\">Eesti</a> <a class=\"fi\" href=\"http://fi.lipsum.com/\">Suomi</a> <a class=\"fr\" href=\"http://fr.lipsum.com/\">Fran�ais</a> <a class=\"ka\" href=\"http://ka.lipsum.com/\">&#4325;&#4304;&#4320;&#4311;&#4323;&#4314;&#4312;</a> <a class=\"de\" href=\"http://de.lipsum.com/\">Deutsch</a> <a class=\"el\" href=\"http://el.lipsum.com/\">&#917;&#955;&#955;&#951;&#957;&#953;&#954;&#940;</a> <span class=\"ltr\" dir=\"ltr\"><a class=\"xx\" href=\"http://he.lipsum.com/\"><img src=\"./lorem_files/he.gif\" width=\"18\" height=\"12\" border=\"0\" alt=\"&#8235;&#1506;&#1489;&#1512;&#1497;&#1514;\"></a><a class=\"xx\" href=\"http://he.lipsum.com/\">&#8235;&#1506;&#1489;&#1512;&#1497;&#1514;</a></span>&nbsp;&nbsp; <a class=\"hu\" href=\"http://hu.lipsum.com/\">Magyar</a> <a class=\"id\" href=\"http://id.lipsum.com/\">Indonesia</a> <a class=\"it\" href=\"http://it.lipsum.com/\">Italiano</a> <br><a class=\"lv\" href=\"http://lv.lipsum.com/\">Latviski</a> <a class=\"lt\" href=\"http://lt.lipsum.com/\">Lietuvi�kai</a> <a class=\"mk\" href=\"http://mk.lipsum.com/\">&#1084;&#1072;&#1082;&#1077;&#1076;&#1086;&#1085;&#1089;&#1082;&#1080;</a> <a class=\"ms\" href=\"http://ms.lipsum.com/\">Melayu</a> <a class=\"no\" href=\"http://no.lipsum.com/\">Norsk</a> <a class=\"pl\" href=\"http://pl.lipsum.com/\">Polski</a> <a class=\"pt\" href=\"http://pt.lipsum.com/\">Portugu�s</a> <a class=\"ro\" href=\"http://ro.lipsum.com/\">Rom�na</a> <a class=\"ru\" href=\"http://ru.lipsum.com/\">Pycc&#1082;&#1080;&#1081;</a> <br><a class=\"sr\" href=\"http://sr.lipsum.com/\">&#1057;&#1088;&#1087;&#1089;&#1082;&#1080;</a> <a class=\"sk\" href=\"http://sk.lipsum.com/\">Sloven&#269;ina</a> <a class=\"sl\" href=\"http://sl.lipsum.com/\">Sloven�&#269;ina</a> <a class=\"es\" href=\"http://es.lipsum.com/\">Espa�ol</a> <a class=\"sv\" href=\"http://sv.lipsum.com/\">Svenska</a> <a class=\"th\" href=\"http://th.lipsum.com/\">&#3652;&#3607;&#3618;</a> <a class=\"tr\" href=\"http://tr.lipsum.com/\">T�rk�e</a> <a class=\"uk\" href=\"http://uk.lipsum.com/\">&#1059;&#1082;&#1088;&#1072;&#1111;&#1085;&#1089;&#1100;&#1082;&#1072;</a> <a class=\"vi\" href=\"http://vi.lipsum.com/\">Ti&#7871;ng Vi&#7879;t</a> </div>\n" +
            "\n" +
            "<div id=\"HdrLeft\"></div>\n" +
            "<div id=\"HdrRight\"></div>\n" +
            "<h1><span>Lorem Ipsum</span></h1>\n" +
            "\n" +
            "<h4>\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\"</h4>\n" +
            "<h5>\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"</h5>\n" +
            "<div class=\"box\"><div style=\"float:right;margin-left:6px;margin-bottom:6px;\"><a target=\"_blank\" href=\"http://www.cafepress.com/lipsum/\"><img src=\"./lorem_files/lipsum08.gif\" width=\"100\" height=\"100\" border=\"0\" alt=\"Lipsum\"></a><br><a target=\"_blank\" href=\"http://www.cafepress.com/lipsum/\"><img src=\"./lorem_files/lipsum05.gif\" width=\"100\" height=\"100\" border=\"0\" alt=\"Lipsum\"></a><br><a target=\"_blank\" href=\"http://www.cafepress.com/lipsum/\"><img src=\"./lorem_files/lipsum04.gif\" width=\"100\" height=\"100\" border=\"0\" alt=\"Lipsum\"></a><br><a target=\"_blank\" href=\"http://www.cafepress.com/lipsum/\"><img src=\"./lorem_files/lipsum07.gif\" width=\"100\" height=\"100\" border=\"0\" alt=\"Lipsum\"></a><br></div><!--\n" +
            "\n" +
            "\n" +
            "If you want to use Lorem Ipsum within another program please contact us for details\n" +
            "on our API rather than parse the HTML below, we have XML and JSON available.\n" +
            "\n" +
            "\n" +
            " --><div id=\"lipsum\">\n" +
            "<p>\n" +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper maximus nunc non aliquet. Nam id elit non dolor posuere ultricies at et urna. Morbi vulputate vitae erat tempor rhoncus. Morbi dictum luctus leo nec euismod. Nam volutpat aliquam mauris, at venenatis ex tincidunt eu. Fusce ac orci ut nisl hendrerit pellentesque. Nunc elementum elit a risus venenatis, sed pretium dui sagittis. Fusce eu arcu urna. Curabitur bibendum ut enim non dapibus. Etiam in ex lacinia, accumsan dolor id, gravida elit. Vivamus orci tortor, porttitor vel laoreet ut, maximus sit amet massa. Sed porta elit eget tincidunt vulputate. Curabitur scelerisque quam eget eros ornare tempor.\n" +
            "</p>\n" +
            "<p>\n" +
            "Vivamus ornare mattis posuere. Duis ultrices ante vitae nisi ultricies sodales. Duis quis orci ut dolor laoreet tincidunt. Curabitur feugiat nunc quis dolor condimentum, et tristique lacus commodo. Pellentesque augue urna, lobortis in nunc a, mollis ultricies dui. Duis sed dignissim neque. Suspendisse volutpat lorem eget pellentesque lacinia. Mauris sodales eu eros non aliquet. Morbi porttitor tellus vel consequat convallis. Vivamus ullamcorper turpis vel justo cursus, sit amet auctor enim rutrum.\n" +
            "</p>\n" +
            "<p>\n" +
            "Morbi arcu leo, scelerisque ut mollis sit amet, finibus at leo. Proin convallis sapien a cursus posuere. Nunc sollicitudin cursus justo rhoncus pulvinar. Sed efficitur rutrum purus dapibus consequat. Maecenas et auctor ligula. Aliquam a eros neque. Duis pulvinar, tortor eu bibendum gravida, ante leo commodo elit, dictum posuere quam dolor sed nulla. Aliquam vel dolor nibh.\n" +
            "</p>\n" +
            "<p>\n" +
            "Duis ac nisl lobortis, interdum nisi in, pulvinar mi. Nunc mollis lectus congue, interdum augue ac, porta est. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut erat mauris, consectetur nec mauris in, ultrices dictum nisi. Phasellus vitae felis tincidunt urna imperdiet tristique. Donec a gravida orci. Etiam a sollicitudin turpis. Proin fringilla tempus sapien, et congue orci tempor sit amet.\n" +
            "</p>\n" +
            "<p>\n" +
            "Maecenas a metus ante. Phasellus imperdiet dapibus metus, nec commodo augue maximus ac. Mauris ac tellus sem. Mauris pulvinar velit nisi, eget suscipit ligula sagittis sed. Duis in accumsan ligula. Aliquam vulputate aliquam dolor vitae dapibus. Etiam vel eros eget justo scelerisque pretium quis eu felis. Phasellus at blandit neque. Vivamus dictum congue ex, et sollicitudin mi suscipit id. Curabitur nec pretium massa, ut elementum orci.\n" +
            "</p></div>\n" +
            "<div id=\"generated\">Generated 5 paragraphs, 360 words, 2393 bytes of <a href=\"http://www.lipsum.com/\" title=\"Lorem Ipsum\">Lorem Ipsum</a></div>\n" +
            "</div><div class=\"box\"><img src=\"./lorem_files/email.gif\" width=\"87\" height=\"14\" alt=\"\" style=\"margin:6px;\"></div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "</div>\n" +
            "\n" +
            "</div>\n" +
            "\n" +
            "<script type=\"text/javascript\">\n" +
            "var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");\n" +
            "document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));\n" +
            "</script><script src=\"./lorem_files/ga.js\" type=\"text/javascript\"></script>\n" +
            "<script type=\"text/javascript\">\n" +
            "try {\n" +
            "var pageTracker = _gat._getTracker(\"UA-15036679-1\");\n" +
            "pageTracker._setDomainName(\".lipsum.com\");\n" +
            "pageTracker._trackPageview();\n" +
            "} catch(err) {}</script>\n" +
            "\n" +
            "\n" +
            "</body></html>";
}
