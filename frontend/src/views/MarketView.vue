<template>
	<two-button-dialog ref="credentialDialog" title="Paypal Credential" cardStyle="width : 50vw">
		<template v-slot="{data}">
			<q-card-section class="column">
				<paypal-credential-data v-model:username="data.username" v-model:password="data.password" />
			</q-card-section>
		</template>
	</two-button-dialog>
	<q-page class="q-pa-sm">
		<q-card class="bg-primary">
			<q-card-section class="row justify-center">
				<div class="text-white text-h3">Market</div>
			</q-card-section>
			<q-separator dark />
			<q-card-section class="row">
				<q-btn class="row" glossy color="light-blue-7" @click="testPay()">Buy RP</q-btn>
			</q-card-section>
		</q-card>
	</q-page>
</template>

<script>
import { defineComponent, onMounted, ref  } from "vue";
import { useStore } from 'vuex'
import TwoButtonDialog from "../components/TwoButtonDialog.vue";
import PaypalCredentialData from "./components/PaypalCredentialData.vue";
import paymentService from "../script/services/PaymentService.js"
import * as PopUpDialog from "../script/utils/PopupDialog.js"

export default defineComponent ({
	components : {
		TwoButtonDialog,
		PaypalCredentialData
	}, 
	setup() {
		const store = useStore()
		const credentialDialog = ref(null)

		const pay = async (item) => {
			try {
				const result = await credentialDialog.value.run({
					username: item?.username,
					password: item == null ? null : undefined
				})
				
				if (result == null) return;
				const token = await paymentService.getToken(null, result)
				console.log(token)
			} catch(err) {
				PopUpDialog.show(store, PopUpDialog.FAILURE, err.message)
			}
		}

		const testPay = async () => {
			try {
				const input = {
					username: "Ae_PSlqhfsx-djBaNcJDRjkAULl55GdNK6pMRldvsvDK8l5oKTA2G6_g4J_9SWDUOBt_BAHHQZjkHp28",
					password: "EHMnoppN5ELfagrqUQInFP9Q_gIYmxmTD4Eqm_6u08ECApkXJYCUjpmQ_37dJG7k6_LWycLG-zwpg4Lk"
				}
				const token = await paymentService.getToken(null, input)
				console.log(token)
			} catch(err) {
				PopUpDialog.show(store, PopUpDialog.FAILURE, err.message)
			}
		}

		onMounted(() => console.log("Market page"))

		return { credentialDialog, pay, testPay }
	}
})
</script>