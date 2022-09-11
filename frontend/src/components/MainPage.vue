<template>
  <div class="ShaFoPu">
    ShaFoPu
  </div>

  <div class="select">
    <select @change="onChange($event)" class="form-control">
      <option v-for="item in options" v-bind:key="item">
        {{ item.key.caption }}
      </option>
    </select>
    <div class="select_arrow"/>
  </div>

  <table>
    <tbody>
    <tr v-for="item in selectedOption.params" v-bind:key="item">
      <td> {{ item.caption }}</td>
      <td>
        <input v-model="this.params[item.name]" placeholder="Введите данные">
      </td>
    </tr>
    </tbody>
  </table>

  <button v-on:click="createCompositeButton" class="myButton">
    Создать композит
  </button>
</template>

<script>
import api from '../api/Api.js'

export default {
  name: 'MainPage',
  data: function () {
    return {
      options: [],
      selectedOption: '',
      params: {}
    }
  },
  created() {
    api.getServicesMeta().then((response) => {
          let data = response.data;
          for (let i = 0; i < data.length; i++) {
            let value = data[i];
            let key = value[0];
            let params = value.slice(1, value.length);
            this.options.push({'key': key, 'params': params});
          }

          this.selectedOption = this.options[0];

          this.clearParams();
        },
        (error) => {
          console.log(error);
        })
  },
  methods: {
    clearParams() {
      for (let i = 0; i < this.selectedOption.params.length; i++) {
        this.params[this.selectedOption.params[i].name] = '0';
      }
    },
    onChange(event) {
      let key = event.target.value;
      let options = this.options.filter(function (element) {
        return element.key.caption === key;
      })
      this.selectedOption = options[0];
      this.clearParams();
    },
    createCompositeButton() {
      api.createComposite(this.params, {'type': this.selectedOption.key.name}).then((response) => {
            const fileURL = window.URL.createObjectURL(new Blob([response.data]));
            const fileLink = document.createElement('a');

            fileLink.href = fileURL;
            fileLink.setAttribute('download', 'composite.lgw');
            document.body.appendChild(fileLink);

            fileLink.click();
          },
          (error) => {
            console.log(error.message);
          })
    }
  }
}
</script>

<style>
.myButton {
  background: #ededed linear-gradient(to bottom, #ededed 5%, #dfdfdf 100%);
  border-radius: 6px;
  border: 1px solid #dcdcdc;
  display: block;
  cursor: pointer;
  color: #000000;
  font-family: Arial;
  font-size: 15px;
  font-weight: bold;
  padding: 6px 24px;
  text-decoration: none;
  text-shadow: 0px 1px 0px #ffffff;
  margin-top: 15px;
}

.myButton:hover {
  background: #dfdfdf linear-gradient(to bottom, #dfdfdf 5%, #ededed 100%);
}

.myButton:active {
  position: relative;
  top: 1px;
}


.ShaFoPu {
  font-family: Arial, sans-serif;
  font-size: 2em;
  line-height: 1.3em;
  vertical-align: top;
  text-align: left;
  color: #000000;
  padding: 0.5em;

  width: 110pt;
  border: none;
  border-radius: 2pt;
  box-shadow: 0 0 0 1pt grey;
  outline: none;
  transition: .1s;
}


.select {
  position: relative;
  left: 1em;
  display: block;
  top: 15px;
  margin-top: 15px;
  margin-bottom: 15px;
  width: 25%;
}

.select select {
  margin-bottom: 15px;
  font-family: 'Arial';
  display: inline-block;
  width: 100%;
  cursor: pointer;
  padding: 10px 22px;
  outline: 0;
  border: 1px solid #000000;
  border-radius: 14px;
  background: #efefef;
  color: #000000;
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
}

.select select::-ms-expand {
  display: none;
}

.select select:hover,
.select select:focus {
  color: #000000;
  background: #efefef;
}

.select select:disabled {
  opacity: 0;
  pointer-events: none;
}

.select_arrow {
  position: absolute;
  top: 18px;
  right: 15px;
  pointer-events: none;
  border-style: solid;
  border-width: 8px 5px 0px 5px;
  border-color: #7b7b7b transparent transparent transparent;
}

.select select:hover ~ .select_arrow,
.select select:focus ~ .select_arrow {
  border-top-color: #000000;
}

.select select:disabled ~ .select_arrow {
  border-top-color: #cccccc;
}
</style>