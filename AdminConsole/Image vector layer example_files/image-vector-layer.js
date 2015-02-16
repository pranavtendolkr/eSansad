var categories = [
"Education",
"Drinking Water Facility",
"Other Public Facilities",
"Health And Family Welfare",
"Roads Pathways And Bridges",
"Irrigation",
"Sports",
"Electricity Facility",
"Non-Conventional Energy Sources"
];

var map = new ol.Map({
  layers: [
    new ol.layer.Tile({
      source: new ol.source.MapQuest({layer: 'sat'})
    }),
    new ol.layer.Image({
      source: new ol.source.ImageVector({
        source: new ol.source.GeoJSON({
          projection: 'EPSG:3857',
          url: './Image vector layer example_files/india_states.geojson'
        }),
        style: new ol.style.Style({
          fill: new ol.style.Fill({
            color: 'rgba(255, 255, 255, 0.6)'
          }),
          stroke: new ol.style.Stroke({
            color: '#319FD3',
            width: 1
          })
        })
      })
    })
  ],
  controls: [],
  target: 'map',
  view: new ol.View({
    center: ol.proj.transform([82, 22], 'EPSG:4326', 'EPSG:3857'),
    zoom: 4.3
  })
});



var featureOverlay = new ol.FeatureOverlay({
  map:  map,
  style: new ol.style.Style({
    stroke: new ol.style.Stroke({
      color: '#f00',
      width: 1
    }),
    fill: new ol.style.Fill({
      color: 'rgba(255,0,0,0.1)'
    })
  })
});

var highlight, fixed = false;
var displayFeatureInfo = function(pixel) {

  var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    return feature;
  });

  //var info = document.getElementById('info');
 
  if (feature) {
    //info.innerHTML = feature.j.ENGTYPE_1 + ': ' + feature.j.NAME_1;
    stateName = feature.j.ENGTYPE_1 + ': ' + feature.j.NAME_1;
  
  } 
  
  if (feature !== highlight) {
    if (highlight) {
      featureOverlay.removeFeature(highlight);
    }
    if (feature) {
      featureOverlay.addFeature(feature);
    }
    highlight = feature;
  }

};
var stateName ;
var selectList= function(pixel) {

  var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    return feature;
  });

  
  var constituencyList = document.getElementById("constituencyList");

  if (feature) {
    stateName = feature.j.NAME_1;
    if(constituency[feature.j.NAME_1]){
      var list = "";
      var arr = constituency[feature.j.NAME_1];
      var i = 0;
      for(i = 0; i < arr.length; i++)
        list += "<option>" + arr[i] + "</option>";
      constituencyList.innerHTML = list; 
    }
  } 
  
  if (feature !== highlight) {
    if (highlight) {
      featureOverlay.removeFeature(highlight);
    }
    if (feature) {
      featureOverlay.addFeature(feature);
    }
    highlight = feature;
  }

};

map.on('pointermove', function(evt) {
  if (evt.dragging) {
    return;
  }
  var pixel = map.getEventPixel(evt.originalEvent);
  displayFeatureInfo(pixel);
});

map.on('click', function(evt) {
  selectList(evt.pixel);
    var feature = map.forEachFeatureAtPixel(evt.pixel, function(feature, layer) {
      var info = document.getElementById("info");
      info.innerHTML = feature.j.ENGTYPE_1 + ': ' + feature.j.NAME_1;
    return feature;
  });


});

var pieData = [];
function renderPieChart(){
  var constituencyName = document.getElementById("constituencyList").value;
  if(constituencyName){
    $.post("./getPiechartInformation.php", {"state": stateName, "constituency": constituencyName}, 
      function(data){
        console.log(data);

        var info = JSON.parse(data);
        var i;
        pieData = [["sd", "sd"]];
        for(i=0; i<categories.length; i++){
          pieData.push([categories[i], parseFloat(info[categories[i]])]);
        }

        drawChart();
      });
  }
}

google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
  console.log(pieData);
  var data = google.visualization.arrayToDataTable(pieData);

  var options = {
    title: 'Categorywise Spend'
  };

  var chart = new google.visualization.PieChart(document.getElementById('piechart'));
  google.visualization.events.addListener(chart, 'click', function(targetid){
    console.log("Triggered");
    displayInformation();
  });

  
  chart.draw(data, options);

 }
 var globalProjects ;
 function displayInformation(){
    var constituencyName = document.getElementById("constituencyList").value;
    var browser = document.getElementById("browser");
    var project = document.getElementById("project")
    browser.style.display = "None";
    project.style.display = "block";
    var projects ;
     $.post("./getProjectInformation.php", {"state": stateName, "constituency": constituencyName} , function( data ) {
      
      projects = JSON.parse(data)["projects"];
      globalProjects = projects;
      var i, line = "";
      globalProjects = projects;
      for(i = 0; i< projects.length; i++){
          line += "<div class = 'row'><h1>" +projects[i]["title"]+ "</h1><p>" + projects[i]["description"] + 
                   "</p><h4>" + projects[i]["category"] + "</h4><h3>" + projects[i]["cost"]+ "</h3></div>" ; 
      }
      project.innerHTML = line;                        
      });
}
    //projects = JSON.parse(projects.responseText);
     
 
