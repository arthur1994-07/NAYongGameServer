<template>
	<div></div>
</template>

<script>
import { defineComponent, onMounted, toRefs } from "vue";

export default defineComponent({
	props: {
		url: { type: String, default : "" },
		type: { default : "" },

	},
	emits: [

	],
	setup(props) {
		const { url, type } = toRefs(props);

		onMounted(() => console.log(url.value, type.value))


		return {  }
	}
})
</script>