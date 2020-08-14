let username = '';
let peed = {
    init: function () {
        let _this = this;
        _this.initProfile();
        _this.initPostRegistrationFunction()
    },
    initProfile: function () {
        let userName = '';
        const profile = new Vue({
            el: ".user-profile",
            data:{
                userName:userName
            }
        })
        axios.get("/session/user")
            .then(function (response) {
                console.log(response);
                userName = response.data['name'];
                username = response.data['name'];
                profile.userName = userName;
            }).catch(function (error) {
            console.log(error);
        })
    },
    initPostRegistrationFunction: function () {
        new Vue({
            el: "#post-card",
            data: {
                content: '',
                placeholder: username + "님, 무슨 생각을 하고 계신가요?"
            },
            methods: {
                register: function (e) {
                    axios.post("/post/register", {
                        content: this.content
                    }).then(function (response) {
                        console.log(response);
                        alert("게시물을 작성했습니다. ");
                        window.location.reload();
                    }).catch(function (error) {
                        console.log(error);
                    });
                }
            }
        })
    }
}
peed.init();