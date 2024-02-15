<template>
	<q-page class="q-pa-sm">
		<q-card-section>
			<div class="text-white text-h3">Market</div>
		</q-card-section>
	</q-page>
</template>

<script>
import { defineComponent, onMounted } from "vue";

export default defineComponent ({
	setup() {

		onMounted(() => console.log("Market page"))
	}
})
</script>