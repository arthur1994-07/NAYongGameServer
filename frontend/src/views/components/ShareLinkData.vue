<template>
	<meta property="og:url" content="" />
    <meta property="og:type" content="" />
    <meta property="og:title" content="" />
    <meta property="og:description" content="" />
    <meta property="og:image" content="" />
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
    props: {
        url: { type: String, default : "" },
        type: { type: any , default : "" },

    },
    emits: [

    ],
    setup() {
        
        return { }
    }
})
</script>