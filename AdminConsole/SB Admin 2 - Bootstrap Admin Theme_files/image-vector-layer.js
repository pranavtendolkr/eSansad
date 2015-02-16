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
  target: 'map',
  view: new ol.View({
    center: ol.proj.transform([82, 22], 'EPSG:4326', 'EPSG:3857'),
    zoom: 4.3
  })
});

var featureOverlay = new ol.FeatureOverlay({
  map: map,
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

var highlight;
var displayFeatureInfo = function(pixel) {

  var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    return feature;
  });

  var info = document.getElementById('info');
 
  if (feature) {
    info.innerHTML = feature.j.ENGTYPE_1 + ': ' + feature.j.NAME_1;
  
  } else {
    info.innerHTML = '&nbsp;';
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

var selectList= function(pixel) {

  var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    return feature;
  });

  
  var constituencyList = document.getElementById("constituencyList");

  if (feature) {
    if(constituency[feature.j.NAME_1]){
      var list = "";
      var arr = constituency[feature.j.NAME_1];
      var i = 0;
      for(i = 0; i < arr.length; i++)
        list += "<option>" + arr[i] + "</option>";
      constituencyList.innerHTML = list; 
    }
  } 
  else {
    info.innerHTML = '&nbsp;';
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
});


google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {

  var data = google.visualization.arrayToDataTable([
    ['Task', 'Hours per Day'],
    ['Work',     11],
    ['Eat',      2],
    ['Commute',  2],
    ['Watch TV', 2],
    ['Sleep',    7]
  ]);

  var options = {
    title: 'Categorywise Spend'
  };

  var chart = new google.visualization.PieChart(document.getElementById('piechart'));
  google.visualization.events.addListener(chart, 'click', function(targetid){console.log("Executing"); });

  
  chart.draw(data, options);

 }