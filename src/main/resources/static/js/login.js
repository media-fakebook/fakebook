let login = {
    init: function () {
        let _this = this;
        _this.initJoin();
    },
    initJoin: function () {
        let currentYear = new Date().getFullYear();
        let countOfYear = 100;
        let countOfMonths = 12;
        let countOfDays = 31;
        let years = [];
        let months = [];
        let days = [];

        for (let i = 0; i < countOfYear; i++) {
            years[i] = currentYear - i;
        }
        for (let i = 0; i < countOfMonths; i++) {
            months[i] = i + 1;
        }
        for (let i = 0; i < countOfDays; i++) {
            days[i] = i + 1;
        }

        new Vue({
            el: "#join-form",
            data: {
                years: years,
                months: months,
                days: days,
                errors: [],
                userId: '',
                password: null,
                name: null,
                gender: '',
                birthdayYear: '연도',
                birthdayMonth: '월',
                birthdayDay: '일'
            },
            methods: {
                validateInput(e) {
                    console.log("validating")
                    this.errors = [];
                    if (!this.name) {
                        this.errors.push("이름을 입력해 주세요.");
                    }
                    if (!this.userId) {
                        this.errors.push("휴대폰 번호 또는 이메일을 입력해 주세요. ");
                    }
                    if (!this.password) {
                        this.errors.push("비밀번호를 입력해 주세요. ");
                    }
                    if (!(Number.isInteger(this.birthdayYear) && Number.isInteger(this.birthdayMonth) &&
                        Number.isInteger(this.birthdayDay))) {
                        this.errors.push("생일 정보를 입력해 주세요.");
                    }
                    if (!this.gender) {
                        this.errors.push("성별을 입력해 주세요. ");
                    }
                    if (this.errors.length) {
                        return false;
                    }
                    return true;
                },
                register(e) {
                    if (this.validateInput(e)) {
                        axios.post('/member/register', {
                            userId: this.userId,
                            password: this.password,
                            name: this.name,
                            gender: this.gender,
                            birthdayYear: this.birthdayYear,
                            birthdayMonth: this.birthdayMonth,
                            birthdayDay: this.birthdayDay
                        }).then(function (response) {
                            console.log(response);
                            alert("회원가입이 완료되었습니다. ")
                        }).catch(function (error) {
                            alert(error.response.data.message);
                        });
                    }
                }
            }
        })
    },
}
login.init();