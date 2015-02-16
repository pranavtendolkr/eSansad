<!DOCTYPE html>
<html lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Admin</title>

    <!-- Bootstrap Core CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/bootstrap.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/metisMenu.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/font-awesome.css" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="./Image vector layer example_files/ol.css" type="text/css">
  
    <link rel="stylesheet" href="./Image vector layer example_files/layout.css" type="text/css">
   

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body  style="padding:0">
    <script>
        var xmlhttp = new XMLHttpRequest();
        var url = "./Image vector layer example_files/constituency.json";
        var constituency;
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                var myArr = JSON.parse(xmlhttp.responseText);
                constituency = myArr;
            }
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.send();



        function populateConst(val) {
          var arr = constituency[val];
          var i = 0;
          var list = "";
          for(i = 0; i < arr.length; i++) {
            list += "<option>" + arr[i] + "</option>";
            console.log(i);
          }
          console.log(list);
          $("#constituency_select").find('option')
                                    .remove()
                                    .end()
                                    .append(list); 
        }


    </script>    

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.php">eSansad Admin - Ministry Of Statistics & Programme Implementation</a>
            </div>
            <!-- /.navbar-header -->
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav in" id="side-menu">
                        <!--<li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input class="form-control" placeholder="Search..." type="text">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    
                                </button>
                            </span>
                            </div>
                        </li>-->

                        <li>
                            <a href="index.php">Dashboard</a>
                        </li>
                        <li>
                            <a class="active" href="add_project.php">Add Project</a>
                        </li>

                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
        <div style="min-height: 380px;" id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header">Add a Project</h2>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <!-- /.row -->
            
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-6">
                             <?php 
                                if(isset($_GET['success']) && $_GET['success'] == "true")  {
                                    echo '<div class="alert alert-success">You Project was added successfully.</div>';
                                } else if (isset($_GET['success']) && $_GET['success'] == "false") {
                                    echo '<div class="alert alert-warning">There was a problem saving the project.</div>';
                                }

                            ?>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Add a Project
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form role="form" name="addProject" method="post" enctype="multipart/form-data" action="add_project_submit.php">
                                        <div class="form-group">
                                            <label>State</label>
                                            <select class="form-control" id="state_select" name="state" onchange="populateConst(this.value)">
                                                <option>Select a State</option>
                                                <option value="Andaman and Nicobar Islands">Andaman and Nicobar Islands</option>
                                                <option value="Andhra Pradesh">Andhra Pradesh</option>
                                                <option value="Arunachal Pradesh">Arunachal Pradesh</option>
                                                <option value="Assam">Assam</option>
                                                <option value="Bihar">Bihar</option>
                                                <option value="Chandigarh">Chandigarh</option>
                                                <option value="Chhattisgarh">Chhattisgarh</option>
                                                <option value="Dadra and Nagar Haveli">Dadra and Nagar Haveli</option>
                                                <option value="Daman and Diu">Daman and Diu</option>
                                                <option value="Delhi">Delhi</option>
                                                <option value="Goa">Goa</option>
                                                <option value="Gujarat">Gujarat</option>
                                                <option value="Haryana">Haryana</option>
                                                <option value="Himachal Pradesh">Himachal Pradesh</option>
                                                <option value="Jammu and Kashmir">Jammu and Kashmir</option>
                                                <option value="Jharkhand">Jharkhand</option>
                                                <option value="Karnataka">Karnataka</option>
                                                <option value="Kerala">Kerala</option>
                                                <option value="Lakshadweep">Lakshadweep</option>
                                                <option value="Madhya Pradesh">Madhya Pradesh</option>
                                                <option value="Maharashtra">Maharashtra</option>
                                                <option value="Manipur">Manipur</option>
                                                <option value="Meghalaya">Meghalaya</option>
                                                <option value="Mizoram">Mizoram</option>
                                                <option value="Nagaland">Nagaland</option>
                                                <option value="Orissa">Orissa</option>
                                                <option value="Pondicherry">Pondicherry</option>
                                                <option value="Punjab">Punjab</option>
                                                <option value="Rajasthan">Rajasthan</option>
                                                <option value="Sikkim">Sikkim</option>
                                                <option value="Tamil Nadu">Tamil Nadu</option>
                                                <option value="Tripura">Tripura</option>
                                                <option value="Uttaranchal">Uttaranchal</option>
                                                <option value="Uttar Pradesh">Uttar Pradesh</option>
                                                <option value="West Bengal">West Bengal</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Constituency</label>
                                            <select class="form-control" id="constituency_select" name="constituency">
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Category</label>
                                            <select class="form-control" name="category">
                                                 <option>Select a category</option>
                                                 <option>Drinking Water Facility</option>
                                                 <option>Education</option>
                                                 <option>Electricity Facility</option>
                                                 <option>Non-Conventional Energy Sources</option>
                                                 <option>Health And Family Welfare</option>
                                                 <option>Irrigation</option>
                                                 <option>Roads Pathways And Bridges</option>
                                                 <option>Sports</option>
                                                 <option>Other Public Facilities</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Title</label>
                                            <input class="form-control" name="title">
                                        </div>
                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea class="form-control" rows="3" name="description"></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>Cost</label>
                                            <input class="form-control" placeholder="" name="cost">
                                        </div>
                                        <div class="form-group">
                                            <label>Upload Photo</label>
                                            <input class="form-control" type="file" placeholder="" name="upload">
                                        </div>
                                        <button type="submit" class="btn btn-primary pull-right">Save</button>
                                    </form>
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        <!-- /#page-wrapper -->

        </div> 
      </div>   
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/bootstrap.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/metisMenu.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/raphael-min.js"></script>
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/morris.js"></script>
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="SB%20Admin%202%20-%20Bootstrap%20Admin%20Theme_files/sb-admin-2.js"></script>

    <script src="./Image vector layer example_files/example-behaviour.js" type="text/javascript"></script>
    <script src="./Image vector layer example_files/loader.js" type="text/javascript"></script>
    <script type="text/javascript" src="./Image vector layer example_files/ol.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="./Image vector layer example_files/image-vector-layer.js"></script>
    




</body></html>