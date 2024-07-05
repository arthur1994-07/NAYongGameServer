<template>
	<q-page class="q-pa-sm">
		<q-card-section class="row justify-center">
			<div class="text-white text-h3">Game Info</div>
		</q-card-section>
		<q-separator dark />
		<q-card-section class="row">
			<q-btn class="row" glossy color="light-blue-7" @click="getHtmlContent()">Game Info</q-btn>
		</q-card-section>
		<div>
			<iframe id="myIframe" width="600" height="450" frameborder="0" allowfullscreen />
		</div>
	</q-page>
</template>

<script>
'use strict'
import { defineComponent, onMounted, ref } from "vue";
import { useStore } from "vuex"
import * as PopupDialog from '../script/utils/PopupDialog.js'

export default defineComponent ({
	setup() {
		const store = useStore()
		const url = ref("https://www.youtube.com/watch?v=xX74-lyJVec")
		const embededUrl = ref(null)

		const embedUrl = (link) => {
			const id = link.split("?v=")[1];
			const embedLink = "http://www.youtube.com/embed/" + id;
			document.getElementById("myIframe").src = embedLink
		}

		const getHtmlContent = () => {
			console.log("open page")
			window.open("/htmls/mainx01.html")
		}

		onMounted(() => {
			try {
				console.log("Game info")
				embedUrl(url.value)
				
			} catch(err) {
				PopupDialog.show(store, PopupDialog.FAILURE, err.message)
			}
		})

		return { getHtmlContent, embededUrl }
	}
})
</script>