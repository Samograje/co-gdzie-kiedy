import React, {Component} from 'react';
import {Dimensions} from 'react-native';
import AffiliationDetailsComponent from './AffiliationDetailsComponent';
import request from '../../APIClient';

class AffiliationDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: {
        firstName: '',
        lastName: '',
        location: '',
      },
      error: false,
      validationError: true,
      isLoading: false,
      isSubmitting: false,
      isGrowlVisible: false,
    };
  }

  componentDidMount() {
    this._isMounted = true;
    if (this.props.mode === 'edit') {
      this.loadInitialData();
    }
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  loadInitialData = () => {
    this.setState({
      isLoading: true,
    });
    request(`/api/affiliations/${this.props.id}`)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          data: response,
          isLoading: false,
        });
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          isLoading: false,
          error: true,
        });
      });
  };

  onChange = (fieldName, value) => {
    const newData = {
      ...this.state.data,
      [fieldName]: value,
    };

    this.setState({
      data: newData,
      validationError: !newData.firstName && !newData.lastName && !newData.location,
    });
  };

  sendData = () => {
    const {id, mode} = this.props;

    let url;
    let method;
    if (mode === 'create') {
      url = '/api/affiliations';
      method = 'POST';
    }
    if (mode === 'edit') {
      url = `/api/affiliations/${id}`;
      method = 'PUT';
    }

    this.setState({
      isSubmitting: true,
    });
    request(url, {
      method: method,
      body: JSON.stringify(this.state.data),
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((response) => response.json())
      .then((response) => {
        if (response.success) {
          this.setState({
            isGrowlVisible: true,
          });
          setTimeout(this.props.goBack, 2000);
        } else {
          if (!this._isMounted) {
            return;
          }
          this.setState({
            error: true,
            isSubmitting: false,
          });
        }
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          error: true,
          isSubmitting: false,
        });
      });
  };

  onReject = () => this.props.goBack();

  render() {
    const isWide = Dimensions.get('window').width > 450;
    const {mode} = this.props;
    const {
      data,
      error,
      validationError,
      isLoading,
      isSubmitting,
      isGrowlVisible,
    } = this.state;

    return (
      <AffiliationDetailsComponent
        data={data}
        mode={mode}
        error={error}
        validationError={validationError}
        isLoading={isLoading}
        isSubmitting={isSubmitting}
        isGrowlVisible={isGrowlVisible}
        isWide={isWide}
        onSubmit={this.sendData}
        onReject={this.onReject}
        onChange={this.onChange}
      />
    );
  }
}

export default AffiliationDetailsContainer;
