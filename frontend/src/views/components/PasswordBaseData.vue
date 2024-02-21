<template>
	<div>
		<div class="items-center q-my-sm">
			<q-input :model-value="newPassword" type="password" input-class="text-black" bg-color="grey-3" label-color="primary" 
				label="new password" filled
				:rules="[ val => !!val || 'Field is required']"
				@update:model-value="(value) => $emit('update:newPassword', value)"
			/>
		</div>
		<div class="items-center q-my-sm">
			<q-input v-model="confirmPassword" type="password" input-class="text-black" bg-color="grey-3" label-color="primary" 
				label="confirm password" filled
				:rules="[ 
					val => !!val || 'Field is required',
					val => val === newPassword || 'password must match'
				]"
			/>
		</div>
	</div>
</template>

<script>
'use strict'

import { defineComponent, ref } from "vue"

export default defineComponent({
	props: {
		newPassword: { type : String, default : "" }
	},
	emits: [
		'update:newPassword'
	],
	setup() {
		const confirmPassword = ref(null)
	
		return { confirmPassword }
	}
})
</script>


