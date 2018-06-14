<%-- 
    Document   : UserList
    Created on : Oct 1, 2016, 7:44:53 AM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.pdms.constants.ApplicationConstants" var="constants" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- ============================================
        ============== Vendor JavaScripts ===============
        ============================================= -->
<!--        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="/assets/js/vendor/bootstrap/bootstrap.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/jRespond/jRespond.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/sparkline/jquery.sparkline.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/slimscroll/jquery.slimscroll.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/animsition/js/jquery.animsition.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/screenfull/screenfull.min.js"/>"></script>
<!--/ vendor javascripts -->

<!-- ============================================
       ============== Data Table(s) JavaScripts ===============
       ============================================= -->
<script src="<c:url value="/assets/js/vendor/datatables/js/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/js/jquery.dataTables.columnFilter.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/ColReorder/js/dataTables.colReorder.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/Responsive/js/dataTables.responsive.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/ColVis/js/dataTables.colVis.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/TableTools/js/dataTables.tableTools.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/dataTables.bootstrap.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/daterangepicker/moment.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datetimepicker/js/bootstrap-datetimepicker.min.js"/>"></script>



<!-- ============================================
       ============== Custom JavaScripts ===============
       ============================================= -->
<script src="<c:url value="/assets/js/main.js"/>"></script>
<!--/ custom javascripts -->
<script src="<c:url value="/appjs/CommonUtils.js"/>" type="text/javascript"></script>

<jsp:include page="../sessionpopup.jsp"/>
<jsp:include page="../commons/CommonHeader.jsp"/>   
<script>
$(document).ready(function(){

		$('#convert-table').click( function() {
  var table = $('#my_form').serializeArray();
  var final_results = [];
  var row_patt = /\[(\d+)\]$/; // Gets the row number inside []
  var name_patt = /^[^\[]+/; // Gets the name without the [0]
  $(table).each( function(index, ele){
      // Get the name of input and row number
      var rowNum = parseInt(row_patt.exec(ele.name)[1]);
      var name = name_patt.exec(ele.name);
      
      // Add the name and value to the correct spot in results
      if( final_results[rowNum] === undefined ){
          final_results[rowNum] = {};
      }
      final_results[rowNum][name] = ele.value;
  });
  console.log(final_results);
  alert(JSON.stringify(final_results)); 
  
  
        $.ajax({
            dataType : "json",
            url : "./dataList.htm",
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify(final_results),
            type : 'POST',
            success : function(data) {
                alert(data);
            },
            error : function(jqXHR,textStatus,errorThrown ){
                showPopupError('Error','error : ' + textStatus, 'ok');
            }
        });
});
         
});
</script>
</head>


<body>


<form id="my_form">
<table class="table table-bordered dynatable">
        
        <tbody>
            <tr>
                
                <td><input type="text" name="id[0]" value="1" class="id" /></td>
                <td><input type="text" name="name[0]" value="Billy" /></td>
                <td><input type="text" name="year[0]" value="Home" /></td>
                <td><input type="text" name="month[0]" value="Phone" /></td>
            </tr>
            <tr>
                
                <td><input type="text" name="id[1]" value="2" class="id" /></td>
                <td><input type="text" name="name[1]" value="Bob" /></td>
                <td><input type="text" name="year[1]" value="work" /></td>
                <td><input type="text" name="month[1]" value="Cell" /></td>
            </tr>
			<tr >
               
                <td><input type="text" name="id[2]" value="2" class="id" /></td>
                <td><input type="text" name="name[2]" value="Bob" /></td>
                <td><input type="text" name="year[2]" value="work" /></td>
                <td><input type="text" name="month[2]" value="Cell" /></td>
            </tr>
    </tbody>
    </table>
</form>
<button id="convert-table" type="button">Convert!</button>




</body>