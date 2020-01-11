import React, {Component} from 'react';
import HomepageComponent from './HomepageComponent';
import request from "../../APIClient";

class HomepageContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false,
      affiliationsCount: null,
      computerSetsCount: null,
      hardwareCount: null,
      softwareCount: null,
      isWide: null,
    };
  }

  componentDidMount() {
    this.isMounted = true;
    request('/api/statistics')
      .then((response) => response.json())
      .then((response) => {
        if (!this.isMounted) {
          return;
        }
        this.setState({
          loading: false,
          error: false,
          ...response,
        })
      })
      .catch(() => {
        if (!this.isMounted) {
          return;
        }
        this.setState({
          loading: false,
          error: true,
        });
      })
  }

  componentWillUnmount() {
    this.isMounted = false;
  }

  handleLayout = ({nativeEvent}) => {
    const {width} = nativeEvent.layout;
    this.setState({
      isWide: width > 650,
    });
  };

  render() {
    return (
      <HomepageComponent
        goToAffiliations={() => this.props.push('AffiliationsList')}
        goToComputerSets={() => this.props.push('ComputerSetsList')}
        goToHardware={() => this.props.push('HardwareList')}
        goToSoftware={() => this.props.push('SoftwareList')}
        handleLayout={this.handleLayout}
        {...this.state}
      />
    );
  }
}

export default HomepageContainer;
