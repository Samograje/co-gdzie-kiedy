import React, {Component} from 'react';
import AffiliationDetailsComponent from './AffiliationDetailsComponent';
import request from '../../APIClient';

class AffiliationDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: {},
      isLoading: true,
      error: false,
    };
  }

  componentDidMount() {
    if (this.props.mode === 'edit') {
      this.loadInitialData();
    }
  }

  loadInitialData = () => {
    request(`/api/affiliations/${this.props.id}`)
      .then((response) => response.json())
      .then((response) => {
        this.setState({
          data: response,
          isLoading: false,
        });
      })
      .catch((response) => {
        this.setState({
          error: true,
        });
      });
  };

  onSubmit = () => this.props.goBack();

  onReject = () => this.props.goBack();

  render() {
    const {mode} = this.props;
    const {data, isLoading, error} = this.state;

    return (
      <AffiliationDetailsComponent
        onSubmit={this.onSubmit}
        onReject={this.onReject}
        mode={mode}
        data={data}
        isLoading={isLoading}
        error={error}
      />
    );
  }
}

export default AffiliationDetailsContainer;
