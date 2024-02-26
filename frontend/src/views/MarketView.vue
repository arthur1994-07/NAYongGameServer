<template>
	<two-button-dialog ref="credentialDialog" title="Paypal Credential" cardStyle="width : 50vw">
		<template v-slot="{data}">
			<q-card-section class="column">
				<paypal-credential-data v-model:username="data.username" v-model:password="data.password" />
			</q-card-section>
		</template>
	</two-button-dialog>
	<selection-dialog ref="gamePointsDialog" :selectMultiple="false"
		title="payment method" successText="select" cancelText="cancel"
	>
		<template v-slot="{data}">
			<q-item-section avatar>
				<q-avatar :color="data.avatarColor" size="md" text-color="primary">
					<q-icon color="white" name="mdi-wallet-giftcard" />
				</q-avatar>
			</q-item-section>
			<q-item-section>
				<div class="text-white">{{ data.points }}</div>
			</q-item-section>
		</template>
		<template v-slot:no-data>{{ t('general_no_data') }}</template>
	</selection-dialog>
	<selection-dialog ref="checkOutDialog" :selectMultiple="false"
		title="Check Out" successText="select" cancelText="cancel"
	>
		<template v-slot="{data}">
			<q-item-section>
				<q-img :src="data.picUrl" fit="contain" style="height: 100px; max-width: 250px;" />
			</q-item-section>
		</template>
		<template v-slot:no-data>{{ t('general_no_data') }}</template>
	</selection-dialog>
	<q-page class="q-pa-sm">
		<q-card class="bg-primary">
			<q-card-section class="row justify-center">
				<div class="text-white text-h3">Market</div>
			</q-card-section>
			<q-separator dark />
			<q-card-section class="row">
				<q-btn class="row" glossy color="light-blue-7" @click="createPayment()">Buy Game Points</q-btn>
			</q-card-section>
		</q-card>
	</q-page>
</template>

<script>
import { defineComponent, onMounted, ref, computed } from "vue";
import { useStore } from 'vuex'
import TwoButtonDialog from "../components/TwoButtonDialog.vue";
import SelectionDialog from "../components/SelectionDialog.vue";
import PaypalCredentialData from "./components/PaypalCredentialData.vue";
import paymentService from "../script/services/PaymentService.js"
import * as PopUpDialog from "../script/utils/PopupDialog.js"
import * as gamePointsList from "../script/enums/gamePointsList.js"
import * as paymentType from "../script/enums/paymentType.js"
import * as currency from "../script/enums/currency.js"

export default defineComponent ({
	components : {
		TwoButtonDialog,
		SelectionDialog,
		PaypalCredentialData
	}, 
	setup() {
		const store = useStore()
		const credentialDialog = ref(null)
		const gamePointsDialog = ref(null)
		const selectedItem = ref(null)
		const checkOutDialog = ref(null)
		const pointLists = computed(() => gamePointsList.items.filter(s => s != null))
		const paymentList = computed(() => paymentType.paymentList.filter(s => s != null))


		const createPayment = async () => {
			try {	
				const gamePoints = await gamePointsDialog.value.run({
					items : pointLists.value
				})
				if (gamePoints == null) return
				selectedItem.value = gamePoints.selection

				const paymentMethods = await checkOutDialog.value.run({
					items: paymentList.value
				})
				if (paymentMethods == null) return
				let result = {
					amount: selectedItem.value.price,
					currency: currency.TWD,
					description: "Game Point purchase: " + selectedItem.value.points,
					paymentType: paymentMethods.selection.name,
					paymentIntent: "sale"
				}
				console.log(result)

			} catch(err) {
				PopUpDialog.show(store, PopUpDialog.FAILURE, err.message)
			}
		}

		const getToken = async (item) => {
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

		const testGetToken = async () => {
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

		onMounted(() => {
			console.log("")
		})

		return { credentialDialog, gamePointsDialog, checkOutDialog, selectedItem, paymentList, getToken, testGetToken, createPayment }
	}
})
</script>