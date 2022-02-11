const showToastMessage = (vueInstance, title, msg, variant) => {
    vueInstance.$bvToast.toast(msg, {
        title: title,
        variant: variant
    });
};

export { showToastMessage };
