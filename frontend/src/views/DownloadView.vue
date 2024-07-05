<template>
	<q-page class="q-pa-sm">
		<q-card-section>
			<div class="text-white text-h3">Download</div>
		</q-card-section>
	</q-page>
</template>

<script>
'use strict'

import { defineComponent, onMounted } from "vue";

export default defineComponent({
	setup() {

		onMounted(() => console.log("Download page"))
	}
})
</script>