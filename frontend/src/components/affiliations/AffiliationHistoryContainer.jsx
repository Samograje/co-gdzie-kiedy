import React, {Component} from 'react';
import AffiliationHistoryComponent from './AffiliationHistoryComponent';
import request from "../../APIClient";

class AffiliationHistoryContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            error: false,
            items: [],
            totalElements: null,
        };
    }

    componentDidMount() {
        this.fetchData();
    }

    fetchData = () => {
        request(`/api/affiliations/history`)
            .then((response) => response.json())
            .then((response) => {
                this.setState({
                    loading: false,
                    ...response,
                });
            })
            .catch(() => {
                this.setState({
                    loading: false,
                    error: true,
                });
            })
    };

    render() {
        const columns = [
            {
                name: 'name',
                label: 'Nazwa',
            },
            {
                name: 'validFrom',
                label: 'Ważne od',
            },
            {
                name: 'validTo',
                label: 'Ważne do',
            },
        ];

        return (
            <AffiliationHistoryComponent
                onFetchData={this.fetchData}
                columns={columns}
                {...this.state}
            />
        );
    }
}

export default AffiliationHistoryContainer;
