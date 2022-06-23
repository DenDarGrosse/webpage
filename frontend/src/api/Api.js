import axios from 'axios/'

const COMPOSITE_URL = 'http://localhost:8080/api/composite'
const SERVICES_META_URL = 'http://localhost:8080/api/servicemeta'

class Api {
    createComposite(data, params) {
        return axios.post(COMPOSITE_URL, data, {params: params, responseType: 'blob'});
    }

    getServicesMeta() {
        return axios.get(SERVICES_META_URL);
    }
}

export default new Api()