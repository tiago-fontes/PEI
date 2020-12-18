<template>
  <div>
    <l-map :zoom="zoom" :center="center" style="height: 500px; width: 100%">
      <l-tile-layer :url="url" :attribution="attribution" />
      <l-control :position="'bottomleft'" class="custom-control-watermark">
        Vue2Leaflet Watermark Control
      </l-control>
      <l-marker :latLng="center" :icon="icon">
        <l-popup>
          <p>Latitude: 47.41322</p>
          <p>Longitude: -1.219482</p>
        </l-popup>
      </l-marker>
    </l-map>
  </div>
</template>

<script>
import { latLng, icon } from "leaflet";
import { LMap, LTileLayer, LControl, LMarker, LPopup } from "vue2-leaflet";

export default {
  name: "GeographicDetails",
  components: {
    LMap,
    LTileLayer,
    LControl,
    LMarker,
    LPopup
  },
  data() {
    return {
      zoom: 13,
      center: latLng(47.41322, -1.219482),
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution:
        '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      icon: icon({
        iconUrl:
          "https://i.pinimg.com/originals/37/f4/ec/37f4ec81af9a18592839b987b1f7862a.png",
        iconSize: [32, 37],
        iconAnchor: [16, 37]
      })
    };
  },
  methods: {
    showAlert() {
      alert("Click!");
    }
  }
};
</script>

<style scoped></style>
