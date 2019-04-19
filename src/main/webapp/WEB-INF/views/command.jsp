<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: summer
  Date: 2019/4/11
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="./static/css/layui.css" media="all">

    <style type="text/css">
        html {
            height: 100%;
        }

        body {
            height: 100%;
            background-color: azure;
        }
    </style>
</head>

<body>
<jsp:include page="title.jsp"/>
<div class="layui-row" style="height: 100%;">
    <div class="layui-col-md2" style="height: 100%;">
        <jsp:include page="navigation.jsp"/>
    </div>
    <div class="layui-col-md10" style="height: 100%;">
        <div class="layui-row" style="height: 100%;">
            <div class="layui-col-md2" style="height: 100%;">
                <div class="layui-form">
                    <select  name="dataType" lay-verify="required" lay-filter="dataType">
                        <option value=""></option>
                        <option value="0">string</option>
                        <option value="1">hash</option>
                        <option value="2">list</option>
                        <option value="3">set</option>
                        <option value="4">zset</option>
                    </select>
                </div>
                <div style="height:95.1%;overflow-y:auto;  background-color: rosybrown">
                    <table class="layui-table">
                        <colgroup>
                            <col width="150">
                            <col width="200">
                            <col>
                        </colgroup>
                        <tbody class="allKeys">
                        <tr>
                            <td onclick="typeKey('')">nothing</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="layui-col-md10" style="height: 60%;">
                <div class="layui-row" style="height: 100%;">
                    <div class="layui-col-md12" style="height: 8%;">
                        <button type="button" class="layui-btn layui-btn-warm" style="float:right"
                                onclick="SelectText()">Run
                        </button>
                    </div>
                    <div class="layui-col-md12" style="background-color:aquamarine; height: 92%;">
							<textarea name="" required lay-verify="required" placeholder="请输入" class="layui-textarea"
                                      style="height: 100%;"></textarea>
                    </div>
                </div>
            </div>
            <div class="layui-col-md10" style="height: 40%; overflow-y:auto; ">
                <div id="result"></div>
            </div>
        </div>
    </div>
</div>
<script src="./static/js/jquery.min.js"></script>
<script type="text/javascript" src="./static/js/layui.js" charset="utf-8"></script>
<script>
    getAllKeys();
    layui.use(['form', 'table'], function () {
        var form = layui.form;
        var table = layui.table;

        form.on('select(dataType)', function (data) {
            var type = "";

            $.ajax({
                type: 'GET',
                url: 'getAllKeys.do',
                data: {
                    type: data.value
                },
                dataType: 'json',
                success: function (result) {
                    var element = "";
                    for (var i = 0; i < result.length; i ++) {
                        element += "<tr><td onclick=\"typeKey('" + result[i] + "')\">" + result[i] + "</td></tr>";
                    }

                    $(".allKeys").html(element);
                },
                error: function (result) {
                    alert("error");
                }
            });
            form.render('select');
        })
    });


    function getAllKeys() {
        $.ajax({
            type: 'GET',
            url: 'getAllKeys.do',
            data: {
                type: ""
            },
            dataType: 'json',
            success: function (result) {
                var element = "";
                for (var i = 0; i < result.length; i ++) {
                    element += "<tr><td onclick=\"typeKey('" + result[i] + "')\">" + result[i] + "</td></tr>";
                }

                $(".allKeys").html(element);
            },
            error: function (result) {
                alert("error");
            }
        })
    }

    function SelectText() {
        var txt = window.getSelection ? window.getSelection() : document.selection.createRange().text;
        var command = txt.toString();
        $.ajax({
            type: 'GET',
            url: 'operateRedis.do',
            data: {
                command: command
            },
            dataType: 'json',
            success: function (result) {
                $("#result").html(JSON.stringify(result));
            },
            error: function (result) {
                alert("error");
            }
        })
    }

    function typeKey(e) {
        var command = e;
        $.ajax({
            type: 'GET',
            url: 'getValueByKey.do',
            data: {
                key: command
            },
            dataType: 'json',
            success: function (result) {
                $("#result").html(JSON.stringify(result));
            },
            error: function (result) {
                alert("error");
            }
        })
    }
</script>
</body>

</html>