<template>
  <div class="new-car">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
          >New Car</v-col
        >
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
                  <v-form>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="licensePlate"
                            label="License Plate"
                            type="text"
                            required
                            color="primary"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="brand"
                            label="Brand"
                            type="text"
                            required
                            color="primary"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="model"
                            label="Model"
                            type="text"
                            required
                            color="primary"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="year"
                            label="Year"
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
              <v-btn color="primary" @click="stepper = 2">
                Next
              </v-btn>
            </v-stepper-content>

            <v-stepper-step :complete="stepper > 2" step="2">
              Car Characteristics
            </v-stepper-step>

            <v-stepper-content step="2">
              <v-card elevation="0">
                <v-card-text>
                  <v-form>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="numberOfDoors"
                            label="Number Of Doors"
                            type="number"
                            required
                            color="primary"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-text-field
                            v-model="seats"
                            label="Seats"
                            type="number"
                            required
                            color="primary"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-select
                            v-model="transmission"
                            :items="transmissionItems"
                            label="Transmission"
                            required
                          ></v-select>
                        </v-col>
                        <v-col cols="12" sm="6" lg="3">
                          <v-select
                            v-model="fuel"
                            :items="fuelItems"
                            label="Fuel"
                            required
                          ></v-select>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-form>
                </v-card-text>
              </v-card>
              <v-btn color="primary" @click="stepper = 3">
                Next
              </v-btn>
              <v-btn text @click="stepper = stepper - 1">
                Previous
              </v-btn>
            </v-stepper-content>

            <v-stepper-step :complete="stepper > 3" step="3">
              Car Photo
            </v-stepper-step>

            <v-stepper-content step="3">
              <v-card elevation="0">
                <v-card-text>
                  <v-form>
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
              <v-btn color="primary" @click="stepper = 4">
                Next
              </v-btn>
              <v-btn text @click="stepper = stepper - 1">
                Previous
              </v-btn>
            </v-stepper-content>

            <v-stepper-step step="4">
              Raspberry Info
            </v-stepper-step>
            <v-stepper-content step="4">
              <v-card
                color="grey lighten-1"
                class="mb-12"
                height="200px"
              ></v-card>
              <v-btn color="primary" @click="stepper = 1">
                Next
              </v-btn>
              <v-btn text @click="stepper = stepper - 1">
                Previous
              </v-btn>
            </v-stepper-content>
          </v-stepper>
        </v-col>
      </v-row>
      <v-row justify="end">
        <v-btn color="primary" class="text-capitalize">
          Save
        </v-btn>
        <v-btn outlined color="warning" class="text-capitalize ml-4">
          Cancel
        </v-btn>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import MainLayoutVue from "../../Layouts/MainLayout.vue";
export default {
  name: "NewCar",
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  data() {
    return {
      stepper: 1,
      licensePlate: "",
      brand: "",
      model: "",
      year: "",
      numberOfDoors: 0,
      seats: 0,
      transmission: "",
      fuel: "",
      photo: "",
      raspberryInfo: "",
      errorMsg: null,
      transmissionItems: ["Automatic", "Manual"],
      fuelItems: ["Gasoline", "Diesel", "Eletric", "Hybrid"],
      photoBase64: ""
    };
  },
  methods: {
    saveCar() {
      console.log("New Car");
    },
    updatePhoto(e) {
      let file = e[0];
      let reader = new FileReader();

      reader.onloadend = () => {
        this.photoBase64 = reader.result;
        console.log(this.photoBase64);
      };
      reader.readAsDataURL(file);
    }
  }
};
</script>

<style scoped></style>
