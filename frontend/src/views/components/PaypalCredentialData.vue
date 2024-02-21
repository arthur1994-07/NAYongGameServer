<template>
	<div>
		<div class="items-center q-my-sm">
			<q-input :model-value="username" input-class="text-black" bg-color="grey-3"
				label-color="primary" label="username" filled
				:rules="[
					val => !!val || 'Field is required',
					// val => /^[a-z][a-z0-9-._]*[a-z]$/.test(val) 
					// 	|| 'No space or special character is allowed'
				]"
				@update:model-value="(value) => $emit('update:username', value)"
			/>
		</div>
		<div class="items-center q-my-sm">
			<q-input :model-value="password" type="password" input-class="text-black" bg-color="grey-3" label-color="primary" 
				label="password" filled
				:rules="[ val => !!val || 'Field is required']"
				@update:model-value="(value) => $emit('update:password', value)"
			/>
		</div>
		<slot />
	</div>
</template>

<script>
'use strict'

import { defineComponent } from "vue";

export default defineComponent ({
	props: {
		username: { type : String, default : "" },
		password: { type : String, default : "" }
	},
	emits: [
		'update:username',
		'update:password'
	],
	setup() {

		return {}
	}
})
</script>