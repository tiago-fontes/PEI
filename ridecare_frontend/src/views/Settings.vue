<template>
  <div class="settings">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          Settings
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="100%" elevation="0">
            <v-card-text>
              <v-container>
                <v-row>
                  <span class="font-weight-bold">
                    Email:
                    <span class="font-weight-light">{{ this.email }}</span>
                  </span>
                </v-row>
                <v-row>
                  <span class="font-weight-bold">
                    Company Name:
                    <span class="font-weight-light">{{
                      this.companyName
                    }}</span>
                  </span>
                </v-row>
                <v-row>
                  <v-col cols="12">
                    <v-btn
                      tile
                      block
                      outlined
                      color="primary"
                      class="text-capitalize"
                      >Edit</v-btn
                    >
                  </v-col>
                  <v-col cols="12" class="pt-0">
                    <v-btn tile block color="primary" class="text-capitalize"
                      >Change Password</v-btn
                    >
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          Project Version
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="100%" elevation="0">
            <v-card-text>
              <v-container>
                <v-row>
                  <span class="font-weight-bold">
                    Version
                    <span class="font-weight-light">1.0.0</span>
                  </span>
                </v-row>
                <v-row>
                  <span class="font-weight-bold">
                    Last Update
                    <span class="font-weight-light">2020-12-01</span>
                  </span>
                </v-row>
              </v-container>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          About the Project
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="100%" elevation="0">
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col
                    >Project in partnership with
                    <span class="font-weight-bold">Bosch(Braga)</span> and
                    <span class="font-weight-bold">Universidade do Minho</span>
                    within the scope of the Computer Engineering Project course
                    of the Masters in Computer Engineering.
                  </v-col>
                </v-row>
                <v-row>
                  <v-col> Coordenador: André Ferreira </v-col>
                </v-row>
                <v-row>
                  <v-col class="pt-0" cols="12"> Participantes: </v-col>
                  <v-col class="pt-0" cols="12">
                    <v-container>
                      <v-row class="d-flex justify-center">
                        <v-col
                          cols="12"
                          xs="12"
                          sm="4"
                          md="3"
                          lg="2"
                          v-for="(member, index) in team"
                          :key="index"
                        >
                          <team-member
                            :img="member.img"
                            :name="member.name"
                            :number="member.number"
                          />
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from "../../axios";
import MainLayoutVue from "../Layouts/MainLayout.vue";
import TeamMember from "../Components/TeamMember.vue";

export default {
  components: { TeamMember },
  name: "Settings",
  data() {
    return {
      companyName: "",
      email: "",
      team: [
        {
          name: "André Coutinho",
          number: "PG39284",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "Gabriela Martins",
          number: "A81987",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "João Costa",
          number: "A81822",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "José António Pinto",
          number: "A81317",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "Leandro Costa",
          number: "PG41083",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "Luís Correia",
          number: "A81141",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "Paulo Jorge Martins",
          number: "PG17918",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "Pedro Barbosa",
          number: "A82068",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "Rafaela Silva",
          number: "A79034",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "Rui Santos",
          number: "PG41850",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        },
        {
          name: "Tiago Fontes",
          number: "A80987",
          img:
            "https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png"
        }
      ]
    };
  },
  created() {
    this.$emit("update:layout", MainLayoutVue);
  },
  mounted() {
    axios
      .get(`${process.env.VUE_APP_ROOT_API}/user`)
      .then(res => {
        this.companyName = res.data.companyName;
        this.email = res.data.email;
      })
      .catch(err => {
        console.log(err.response);
      });
  }
};
</script>
