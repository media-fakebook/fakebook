let peed = {
    init: function () {
        let _this = this;
        _this.initProfile();
        _this.initPostRegistrationFunction()
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

    },
    initPostRegistrationFunction: function () {
        new Vue({
            el: "#post-card",
            data: {
                content: ''
            },
            methods: {
                register: function (e) {
                    axios.post("/post/register", {
                        content: this.content
                    }).then(function (response) {
                        console.log(response);
                    }).catch(function (error) {
                        console.log(error);
                    });
                }
            }
        })
    }
}
peed.init();