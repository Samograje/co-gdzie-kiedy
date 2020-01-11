import React, {Component} from 'react';
import SoftwareHistoryComponent from './SoftwareHistoryComponent';
import request from "../../APIClient";

class SoftwareHistoryContainer extends Component {
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
        request(`/api/software/${this.props.id}/computer-sets-history`)
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
                name: 'inventoryNumber',
                label: 'Numer inwentarzowy',
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
            <SoftwareHistoryComponent
                onFetchData={this.fetchData}
                columns={columns}
                {...this.state}
            />
        );
    }
}

export default SoftwareHistoryContainer;
