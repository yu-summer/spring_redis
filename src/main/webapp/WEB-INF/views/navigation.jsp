<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="./static/css/nav.css">
    <link rel="stylesheet" type="text/css" href="./static/fonts/iconfont.css">
    <script type="text/javascript" src="./static/js/jquery.min.js"></script>
    <script type="text/javascript" src="./static/js/nav.js"></script>
</head>
<body>
    <div class="nav">
        <ul>
            <li class="nav-item">
                <a href="javascript:;"><i class="my-icon nav-icon icon_1"></i><span>Dashboard</span><i class="my-icon nav-more"></i></a>
                <ul>
                    <li><a href="javascript:;"><span>Home</span></a></li>
                    <li><a href="chart.do;"><span>Chart</span></a></li>
                </ul>
            </li>
            <li class="nav-item">
                <a href="javascript:;"><i class="my-icon nav-icon icon_2"></i><span>Rules</span><i class="my-icon nav-more"></i></a>
                <ul id="rules">
                    <li><a href="javascript:;"><span>list</span></a></li>
                </ul>
            </li>
            <li class="nav-item">
                <a href="javascript:;"><i class="my-icon nav-icon icon_3"></i><span>Other</span><i class="my-icon nav-more"></i></a>
                <ul>
                    <li><a href="pdf.do"><span>PDF</span></a></li>
                    <li><a href="javascript:;"><span>About</span></a></li>
                </ul>
            </li>
        </ul>
    </div>
</body>
<script type="text/javascript">
    // $(document).ready(function(){
    //     $.ajax({
    //         type: 'GET',
    //         url: 'rulesList.do',
    //         success: function (data) {
    //             var rules = data.result;
    //             var html = "";
    //
    //             for(var i = 0 ; i < rules.length; i++) {
    //                 html += "<li><a href=\"javascript:;\"><span>" + rules[i] + "</span></a></li>";
    //             }
    //
    //             $("#rules").append(html);
    //         }
    //     })
    // })
</script>
</html>


