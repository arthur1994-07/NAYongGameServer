<template>
	<two-button-dialog ref="dialog" :title="title" :successText="successText" :cardClass="cardClass">
		<template v-slot="{data}">
			<q-card-section>
				<div v-if="data.items.length == 0" class="text-primary text-center">
					<slot name="no-data" :data="data">
						No Data
					</slot>
				</div>
				<q-scroll-area v-else class="row full-width" style="height:50vh">
					<q-list
						v-for="(item, index) in data.items"
						:key="index"
						class="row full-width"
					>
						<q-item v-ripple>
							<q-radio keep-color
								v-model="modelOption" 
								class="text-primary row full-width" 
								:val="item" 
								:label="item.display" 
								:active="isSelected(data, item, index)"
								@click="selectItem(data, item, index)"
								color="primary"
							/>
							<slot :data="item" />
						</q-item>
					</q-list>
				</q-scroll-area>
			</q-card-section>
		</template>
	</two-button-dialog>
</template>


<script>
import { defineComponent, ref, toRefs } from "vue"
import TwoButtonDialog from './TwoButtonDialog.vue'


export default defineComponent({
	components: {
		TwoButtonDialog
	},

	props: {
		title: { required: true, type: String },
		cardClass: { type: String , default: "dialog" },
		selectMultiple: { type: Boolean, default: false },
		successText: { type: String, default:"Select"},
	},
	setup(props) { 
		const { selectMultiple } = toRefs(props)
		const dialog = ref(null)
		const modelOption = ref([])

		const run = async (data, property) => {
			const result = await dialog.value.run({
				...data,
				selectedIndices : [...(property?.selectedIndices ?? [])],
			}, {
				successCheck: (property?.supportNoSelection ?? false) ? null : (data) => data.selectedIndices.length != 0
			})
			if (result == null) return null

			var selected = result.selectedIndices.map(s => data.items[s])
			result.selectedIndices = undefined
			result.selection = selectMultiple.value ? selected : (selected.length == 0 ? null : selected[0])
			return result
		}
		
		const selectItem = (data, item, index) => {
			if (!selectMultiple.value) {
				data.selectedIndices = data.selectedIndices.includes(index) ? [] : [index]
				return
			}
			const pos = data.selectedIndices.indexOf(index)
			if (pos < 0) {
				data.selectedIndices.splice(0, 0, index)
			} else {
				data.selectedIndices.splice(pos, 1)
			}
		}

		const isSelected = (data, item, index) => data.selectedIndices.includes(index)

		return { dialog, modelOption, run, isSelected, selectItem }
	}
})
</script>