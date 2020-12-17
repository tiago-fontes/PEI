<template>
  <div class="new-car">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          New Car
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-stepper v-model="stepper" vertical>
            <v-stepper-step :complete="stepper > 1" step="1">
              Car Main Info
              <small></small>
            </v-stepper-step>

            <v-stepper-content step="1">
              <v-card elevation="0">
                <v-card-text>
                  <v-form
                    ref="carMainInfoForm"
                    v-model="carMainInfoFormValidity"
                  >
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="carMainInfoForm.licensePlate"
                            label="License Plate"
                            type="text"
                            required
                            color="primary"
                            :rules="carMainInfoForm.rules.licensePlateRules"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="carMainInfoForm.brand"
                            label="Brand"
                            type="text"
                            required
                            color="primary"
                            :rules="carMainInfoForm.rules.brandRules"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="carMainInfoForm.model"
                            label="Model"
                            type="text"
                            required
                            color="primary"
                            :rules="carMainInfoForm.rules.modelRules"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="carMainInfoForm.year"
                            label="Year"
                            type="text"
                            required
                            color="primary"
                            :rules="carMainInfoForm.rules.yearRules"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-form>
                </v-card-text>
              </v-card>
              <v-btn
                tile
                color="primary"
                :disabled="!carMainInfoFormValidity"
                @click="stepper = 2"
              >
                Next
              </v-btn>
            </v-stepper-content>

            <v-stepper-step :complete="stepper > 2" step="2">
              Car Characteristics
            </v-stepper-step>

            <v-stepper-content step="2">
              <v-card elevation="0">
                <v-card-text>
                  <v-form
                    ref="carCharacteristicsForm"
                    v-model="carCharacteristicsFormValidity"
                  >
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="carCharacteristicsForm.numberOfDoors"
                            label="Number Of Doors"
                            type="number"
                            required
                            :rules="
                              carCharacteristicsForm.rules.numberOfDoorsRules
                            "
                            color="primary"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="carCharacteristicsForm.seats"
                            label="Seats"
                            type="number"
                            required
                            :rules="carCharacteristicsForm.rules.seatRules"
                            color="primary"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-select
                            v-model="carCharacteristicsForm.transmission"
                            :items="carCharacteristicsForm.transmissionItems"
                            label="Transmission"
                            :rules="
                              carCharacteristicsForm.rules.transmissionRules
                            "
                            required
                          ></v-select>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-select
                            v-model="carCharacteristicsForm.fuel"
                            :items="carCharacteristicsForm.fuelItems"
                            label="Fuel"
                            :rules="carCharacteristicsForm.rules.fuelRules"
                            required
                          ></v-select>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-form>
                </v-card-text>
              </v-card>
              <v-btn
                tile
                color="primary"
                @click="stepper = 3"
                :disabled="!carCharacteristicsFormValidity"
              >
                Next
              </v-btn>
              <v-btn tile text @click="stepper = stepper - 1"> Previous </v-btn>
            </v-stepper-content>

            <v-stepper-step :complete="stepper > 3" step="3">
              Car Photo
            </v-stepper-step>

            <v-stepper-content step="3">
              <v-card elevation="0">
                <v-card-text>
                  <v-form ref="carPhotoForm">
                    <v-container>
                      <v-row>
                        <v-col cols="12">
                          <v-file-input
                            small-chips
                            multiple
                            label="Photo"
                            @change="updatePhoto"
                          ></v-file-input>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-form>
                </v-card-text>
              </v-card>
              <v-btn tile color="primary" @click="stepper = 4"> Next </v-btn>
              <v-btn tile text @click="stepper = stepper - 1"> Previous </v-btn>
            </v-stepper-content>

            <v-stepper-step step="4"> Device Info </v-stepper-step>
            <v-stepper-content step="4">
              <v-card elevation="0">
                <v-card-text>
                  <v-form ref="rideCareDeviceForm">
                    <v-container>
                      <v-row>
                        <v-col cols="12">
                          <v-text-field
                            v-model="rideCareDeviceForm.rideCareDeviceId"
                            label="RideCare Device ID"
                            hint="Check your RideCare device"
                            persistent-hint
                            type="text"
                            required
                            color="primary"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-form>
                </v-card-text>
              </v-card>
              <v-btn tile text @click="stepper = stepper - 1"> Previous </v-btn>
            </v-stepper-content>
          </v-stepper>
        </v-col>
      </v-row>

      <v-row justify="end">
        <v-btn
          tile
          color="primary"
          class="text-capitalize"
          :disabled="
            !carMainInfoFormValidity || !carCharacteristicsFormValidity
          "
          @click="saveCar"
        >
          Save
        </v-btn>
        <v-btn tile outlined color="warning" class="text-capitalize ml-4">
          Cancel
        </v-btn>
      </v-row>
    </v-container>
    <v-snackbar
      v-model="snackbar.show"
      :timeout="snackbar.timeout"
      top
      right
      :color="snackbar.color"
    >
      {{ snackbar.message }}
    </v-snackbar>
  </div>
</template>

<script>
import MainLayoutVue from "../../Layouts/MainLayout.vue";
import axios from "../../../axios";

export default {
  name: "NewCar",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  data() {
    return {
      stepper: 1,
      // Forms
      carMainInfoForm: {
        licensePlate: "",
        brand: "",
        model: "",
        year: "",
        rules: {
          licensePlateRules: [v => !!v || "The License Plate is Required"],
          brandRules: [v => !!v || "The Brand is Required"],
          modelRules: [v => !!v || "The Model is Required"],
          yearRules: [
            v => !!v || "The Year is Required",
            v =>
              (!isNaN(parseInt(v)) && v >= 1900 && v <= 2050) ||
              "The Year must be a integer between 1900 and 2050"
          ]
        }
      },
      carCharacteristicsForm: {
        transmissionItems: ["Automatic", "Manual"],
        fuelItems: ["Gasoline", "Diesel", "Eletric", "Hybrid"],
        numberOfDoors: 0,
        seats: 0,
        transmission: "",
        fuel: "",
        rules: {
          numberOfDoorsRules: [v => !!v || "The Number of Doors is Required"],
          seatRules: [v => !!v || "The Seat is Required"],
          transmissionRules: [
            v => !!v.length > 0 || "The Transmission is Required"
          ],
          fuelRules: [v => v.length > 0 || "The Fuel is Required"]
        }
      },
      carPhotoForm: {
        photo: ""
      },
      //Validitys
      rideCareDeviceForm: {
        rideCareDeviceId: ""
      },
      carMainInfoFormValidity: false,
      carCharacteristicsFormValidity: false,
      //Photo Base 64
      photoBase64: "",
      snackbar: {
        show: false,
        message: null,
        timeout: 5000,
        success: false,
        color: null
      }
    };
  },
  methods: {
    saveCar() {
      var obj = {
        brand: this.carMainInfoForm.brand,
        fuel: this.carCharacteristicsForm.fuel,
        image: this.photoBase64,
        licensePlate: this.carMainInfoForm.licensePlate,
        model: this.carMainInfoForm.model,
        numberOfDoors: this.carCharacteristicsForm.numberOfDoors,
        numberOfSeats: this.carCharacteristicsForm.seats,
        transmission: this.carCharacteristicsForm.transmission,
        year: this.carMainInfoForm.year
      };
      axios
        .post(`${process.env.VUE_APP_ROOT_API}/car`, obj)
        .then(res => {
          console.log(res);
          this.resetForms();
          this.snackbar.show = true;
          this.snackbar.message = "Car successfully created";
          this.snackbar.success = true;
          this.snackbar.color = "success";
        })
        .catch(err => {
          console.log(err.response.data.message);
          this.resetForms();
          this.snackbar.show = true;
          this.snackbar.message =
            err.response.data.message + "Please Try Again";
          this.snackbar.success = false;
          this.snackbar.color = "error";
        });
    },
    resetForms() {
      this.stepper = 1;
      this.$refs.carMainInfoForm.reset();
      this.$refs.carCharacteristicsForm.reset();
      this.$refs.carPhotoForm.reset();
      this.$refs.rideCareDeviceForm.reset();
      this.carCharacteristicsForm.transmission = "";
      this.carCharacteristicsForm.fuel = "";
      this.rideCareDeviceForm.rideCareDeviceId = "";
    },
    updatePhoto(e) {
      let file = e[0];
      let reader = new FileReader();

      if (file) {
        reader.readAsDataURL(file);
      }

      reader.onloadend = () => {
        this.photoBase64 = reader.result;
        console.log(this.photoBase64);
      };
    }
  }
};
</script>

<style scoped></style>
