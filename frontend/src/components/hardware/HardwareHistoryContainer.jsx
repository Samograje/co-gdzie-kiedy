import React, {Component} from 'react';
import HardwareHistoryComponent from './HardwareHistoryComponent';
import request from "../../APIClient";

class HardwareHistoryContainer extends Component {
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
        request('/api/hardware/' + this.props.id.toString + '/history/' + this.props.mode.toString)
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

        const columnsAffiliationsMode = [
            {
                name: 'name',
                label: 'Nazwa',
            },
        ];
        const columnsComputerSetsMode = [
            {
                name: 'name',
                label: 'Nazwa',
            },
        ];

        let actualColumns;
        if (this.props.mode === 'affiliations') {
            actualColumns = columnsAffiliationsMode
        }
        if (this.props.mode === 'computersets') {
            actualColumns = columnsComputerSetsMode
        }

        return (
            <HardwareHistoryComponent
                onFetchData={this.fetchData}
                columns={actualColumns}
                {...this.state}
            />
        );
    }
}

export default HardwareHistoryContainer;
