let peed = {
    init: function () {
        let _this = this;
        _this.initProfile();
    },
    initProfile: function () {
        let userName='';
        const profile = new Vue({
            el:".user-profile",
            data:{
                userName:userName
            }
        })
        axios.get("/session/user")
            .then(function (response) {
                console.log(response);
                profile.userName = response.data['name'];
            }).catch(function (error) {
            console.log(error);
        })

    }
}
peed.init();