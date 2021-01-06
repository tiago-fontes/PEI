<template>
  <div class="settings">
    <v-text-field
      color="primary"
      loading
      disabled
      v-if="loading == true"
    ></v-text-field>
    <v-container v-else>
      <v-row>
        <v-col
          cols="12"
          class="text-center text-sm-left text-h6 font-weight-bold"
        >
          Settings
        </v-col>
      </v-row>
      <v-row>
        <v-col class="py-0">
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
                  <!-- EDIT USER DIALOG -->
                  <v-col cols="12">
                    <v-dialog
                      v-model="editUserDialog"
                      persistent
                      max-width="600px"
                    >
                      <template v-slot:activator="{ on }">
                        <v-btn
                          tile
                          block
                          outlined
                          color="primary"
                          class="text-capitalize"
                          v-on="on"
                          >Edit</v-btn
                        >
                      </template>
                      <v-card>
                        <v-card-title>
                          <span
                            class="text-center text-sm-left text-h6 font-weight-bold"
                            >Change Profile</span
                          >
                        </v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-form @submit.prevent="changeProfile">
                              <v-row>
                                <v-col cols="11">
                                  <v-text-field
                                    v-model="editUserForm.email"
                                    label="Email"
                                    type="text"
                                    color="primary"
                                    :disabled="!editUserForm.emailInputEnabled"
                                  />
                                </v-col>
                                <v-col cols="1" align-self="center">
                                  <v-icon
                                    @click="
                                      editUserForm.emailInputEnabled = !editUserForm.emailInputEnabled
                                    "
                                  >
                                    {{
                                      editUserForm.emailInputEnabled
                                        ? "mdi-close"
                                        : "mdi-pencil"
                                    }}
                                  </v-icon>
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-col cols="11">
                                  <v-text-field
                                    v-model="editUserForm.companyName"
                                    label="Company Name"
                                    type="text"
                                    color="primary"
                                    :disabled="
                                      !editUserForm.compNameInputEnabled
                                    "
                                  />
                                </v-col>
                                <v-col cols="1" align-self="center">
                                  <v-icon
                                    @click="
                                      editUserForm.compNameInputEnabled = !editUserForm.compNameInputEnabled
                                    "
                                  >
                                    {{
                                      editUserForm.compNameInputEnabled
                                        ? "mdi-close"
                                        : "mdi-pencil"
                                    }}
                                  </v-icon>
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-btn
                                  text
                                  small
                                  color="primary"
                                  class="text-capitalize"
                                  @click="editUserDialog = false"
                                >
                                  Close
                                </v-btn>
                                <v-btn
                                  tile
                                  small
                                  color="primary"
                                  class="text-capitalize"
                                  type="submit"
                                >
                                  Save
                                </v-btn>
                              </v-row>
                            </v-form>
                          </v-container>
                        </v-card-text>
                      </v-card>
                    </v-dialog>
                  </v-col>
                  <!-- CHANGE PASSWORD DIALOG -->
                  <v-col cols="12" class="pt-0">
                    <v-dialog
                      v-model="changePasswordDialog"
                      persistent
                      max-width="600px"
                    >
                      <template v-slot:activator="{ on }">
                        <v-btn
                          tile
                          block
                          color="primary"
                          class="text-capitalize"
                          v-on="on"
                          >Change Password</v-btn
                        >
                      </template>
                      <v-card>
                        <v-card-title>
                          <span
                            class="text-center text-sm-left text-h6 font-weight-bold"
                            >Change Password</span
                          >
                        </v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-form
                              ref="changePwdForm"
                              v-model="changePasswordForm.validity"
                              @submit.prevent="changePassword"
                            >
                              <v-row>
                                <v-col cols="12">
                                  <v-text-field
                                    v-model="changePasswordForm.actualPassword"
                                    label="Actual Password"
                                    type="password"
                                    color="primary"
                                    required
                                  />
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-col cols="12">
                                  <v-text-field
                                    v-model="changePasswordForm.newPassword"
                                    label="New Password"
                                    type="password"
                                    color="primary"
                                    required
                                    :rules="changePasswordForm.passwordRules"
                                  />
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-col cols="12">
                                  <v-text-field
                                    v-model="
                                      changePasswordForm.repeatNewPassword
                                    "
                                    label="Repeat New Password"
                                    type="password"
                                    color="primary"
                                    required
                                    :rules="[passwordConfirmationRule]"
                                  />
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-btn
                                  text
                                  small
                                  color="primary"
                                  class="text-capitalize"
                                  @click="changePasswordDialog = false"
                                >
                                  Close
                                </v-btn>
                                <v-btn
                                  tile
                                  small
                                  color="primary"
                                  class="text-capitalize bl-1"
                                  type="submit"
                                  :disabled="!changePasswordForm.validity"
                                >
                                  Save
                                </v-btn>
                              </v-row>
                            </v-form>
                          </v-container>
                        </v-card-text>
                      </v-card>
                    </v-dialog>
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
          class="text-center text-sm-left text-h6 font-weight-bold pb-0"
        >
          Project Version
        </v-col>
      </v-row>
      <v-row>
        <v-col class="py-0">
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
import axios from "../../axios";
import MainLayoutVue from "../Layouts/MainLayout.vue";

export default {
  name: "Settings",
  data() {
    return {
      loading: true,
      companyName: "",
      email: "",
      changePasswordDialog: false,
      editUserDialog: false,
      editUserForm: {
        companyName: null,
        email: null,
        emailInputEnabled: false,
        compNameInputEnabled: false
      },
      changePasswordForm: {
        actualPassword: "",
        newPassword: "",
        passwordRules: [v => !!v || "Password is required."],
        repeatNewPassword: "",
        validity: false
      },
      snackbar: {
        show: false,
        message: null,
        timeout: 5000,
        color: null
      }
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
        this.changeSnackbar(err.message, "error");
      })
      .finally(() => (this.loading = false));
  },
  methods: {
    changePassword: function() {
      let data = { password: this.changePasswordForm.newPassword };
      axios
        .patch(`${process.env.VUE_APP_ROOT_API}/user`, data)
        .then(() => {
          //console.log(res);
          this.changePasswordDialog = false;
          this.changeSnackbar("Password successfully changed", "success");
        })
        .catch(err => {
          this.changePasswordDialog = false;
          this.changeSnackbar(err.message, "error");
        });
    },
    changeProfile: function() {
      axios
        .patch(`${process.env.VUE_APP_ROOT_API}/user`, this.editUserForm)
        .then(() => {
          //console.log(res);
          this.editUserDialog = false;
          this.changeSnackbar("Profile successfully changed", "success");
        })
        .catch(err => {
          this.editUserDialog = false;
          this.changeSnackbar(err.message, "error");
        });
    },
    changeSnackbar(message, success, color) {
      this.snackbar.show = true;
      this.snackbar.message = message;
      this.snackbar.color = color;
    }
  },
  computed: {
    passwordConfirmationRule() {
      return (
        this.changePasswordForm.newPassword ===
          this.changePasswordForm.repeatNewPassword || "Passwords must match."
      );
    }
  }
};
</script>
