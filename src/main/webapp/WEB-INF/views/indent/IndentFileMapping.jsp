<%-- 
    Document   : IndentFileMapping
    Created on : Sep 23, 2016, 3:23:17 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.e9 == 1)}"> 

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>PDMS : Indent Form</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>

    </head>
    <body>
        <jsp:include page="../commons/CommonHeader.jsp"/>    
        <spring:form name="fileMapForm" method="post" commandName="indentFileMapObj">
            <section id="main-content">
                <section class="wrapper">
                    <div class="row">
                        <div class="col-lg-12">
                            <section class="panel">
                                <header class="panel-heading">
                                    Map Indent Form
                                </header>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-sm-2">
                                            <label>Group</label>
                                        </div>
                                        <div class="col-sm-4">
                                            <select name="fileGroupID" id="fileGroupID">
                                                <option value="0">Select</option>
                                                <c:if test="${groupLi != null}">
                                                    <c:forEach items="${groupLi}" var="grpObj">
                                                        <option value="${grpObj.groupID}">
                                                            ${grpObj.groupName}
                                                        </option>
                                                    </c:forEach>
                                                </c:if>
                                            </select>
                                            <br/>
                                            <label id="errorfileGroupID" style="color: red"></label>
                                        </div>
                                        <div class="col-sm-2">
                                            <label>Generated File No#</label>
                                        </div>
                                        <div class="col-sm-2">
                                            <input type="text" name="fileNo" value="${fileNo}"
                                                   readonly="true" class="form-control" id="focusedInput"/>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <label id="errorCheckSelect" style="color: red"></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <section class="panel">
                                <div class="panel-body">
                                    <div class="adv-table">
                                        <table  class="display table table-bordered table-striped" id="dynamic-table">
                                            <thead>
                                                <tr>
                                                    <th>&nbsp;</th>
                                                    <th>Indent No#</th>
                                                    <th>Received Date</th>
                                                    <th>Indent Date</th>
                                                    <th>Estimated Cost</th>
                                                    <th>Section</th>
                                                    <th>Item</th>
                                                    <th>Status</th>
                                                </tr>
                                            <tbody>
                                                <c:if test="${indentLi != null}">
                                                    <c:set var="lpCount" value="0"/>
                                                    <c:forEach var="indObj" items="${indentLi}">
                                                        <tr class="gradeX">
                                                            <td> 
                                                                <input type="checkbox" name="mappedIndents"
                                                                       value="${fn:trim(indObj.encFieldValue)}"/>
                                                            </td>
                                                            <td>${indObj.indentNumber}</td>
                                                            <td>${indObj.recevidedDate}</td>
                                                            <td>${indObj.indentDate}</td>
                                                            <td>${indObj.estimatedCost}</td>
                                                            <td>${indObj.sectionObj.sectionName}</td>
                                                            <td>${indObj.itemObj.itemName}</td>
                                                            <td>${indObj.indentStatus}</td>
                                                        </tr>
                                                        <c:set var="lpCount" value="${lpCount+1}"/>
                                                    </c:forEach>
                                                </c:if>
                                            </tbody>
                                            </thead>
                                        </table>
                                    </div>
                                    <div class="row">&nbsp;</div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <span class="tools pull-right">
                                                <input type="button" value="Map Indents" class="btn btn-info" id="mapIndent"/> 
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>

                </section>
            </section>
            <jsp:include page="../commons/CommonJSIncl.jsp"/>
            <script src="<c:url value="/js/dynamic_table_init.js"/>"></script>
            <script>
                $(document).ready(function () {
                    $("#mapIndent").click(function () {
                        if (fnValidateForm())
                        {
                            document.fileMapForm.action = "<c:url value="mapindents.htm"/>";
                            document.fileMapForm.submit();
                        }
                    });
                });

                var flagValidTotal = new Array();
                var returnstring = false;
                function fnValidateForm()
                {
                    $("#errorfileGroupID").html("");
                    $("#errorCheckSelect").html("");

                    var fGrpID = $("#fileGroupID").val();

                    if ($.trim(fGrpID) === '0')
                    {
                        flagValidTotal.push(false);
                        $("#errorfileGroupID").html("Please select Group Name");
                    } else
                    {
                        flagValidTotal.push(true);
                        $("#errorfileGroupID").html("");
                    }

                    if ($('[name="mappedIndents"]:checked').length > 0) {
                        $("#errorCheckSelect").html("");
                        flagValidTotal.push(true);
                    } else {
                        $("#errorCheckSelect").html("Please select Indent(s) to Map");
                        flagValidTotal.push(false);
                    }

                    for (var i = 0; i < flagValidTotal.length; i++) {
                        if (flagValidTotal[i] == false) {
                            returnstring = flagValidTotal[i];
                            flagValidTotal = new Array();
                            break;
                        } else {
                            returnstring = true;
                        }
                    }
                    return returnstring;

                }

            </script>
        </spring:form>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.e9 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>